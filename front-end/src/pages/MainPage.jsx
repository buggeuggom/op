import { useState, useEffect } from 'react';
import DatePicker from 'react-datepicker';
import "react-datepicker/dist/react-datepicker.css";
import { ko } from 'date-fns/locale'; 
import AddWorkModal from '../components/AddWorkModal';
import {
  getDailyWorks,
  createDailyWorks, updateDailyWork, deleteDailyWork,
  updateMonthlyGoal, deleteMonthlyGoal, createMonthlyGoals,
  createRoutineJobs, updateRoutineJob, deleteRoutineJob,
  createProjects, updateProject, deleteProject
} from '../api/dailyWorkApi';
import '../styles/MainPage.css';
import { useNavigate } from 'react-router-dom';

function MainPage() {
  const [dailyData, setDailyData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [weekDates, setWeekDates] = useState([]);
  const [selectedWeekStart, setSelectedWeekStart] = useState(new Date());
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [editingCell, setEditingCell] = useState(null);
  const [editValue, setEditValue] = useState('');
  const [editingMonthlyGoal, setEditingMonthlyGoal] = useState(null);
  const [editMonthlyValue, setEditMonthlyValue] = useState('');
  const [editingRoutineJob, setEditingRoutineJob] = useState(null);
  const [editRoutineValue, setEditRoutineValue] = useState('');
  const [editingProject, setEditingProject] = useState(null);
  const [editProjectValue, setEditProjectValue] = useState('');
  const navigate = useNavigate();

  const getThisWeekDates = (baseDate) => { //TODO: 리펙토링 필요(아주 많이) <= mac에선 잘돌아갔는데 안되서 GPT 쎈세가 변경함
    // baseDate를 한국 시간(UTC+9) 기준으로 변환
    const koreaDate = new Date(baseDate.getTime() + 9 * 60 * 60 * 1000);
    koreaDate.setUTCHours(0, 0, 0, 0);
    
    const currentDay = koreaDate.getUTCDay();

    const monday = new Date(koreaDate);
    monday.setUTCDate(koreaDate.getUTCDate() - (currentDay === 0 ? 6 : currentDay - 1));

    const dates = [];
    for (let i = 0; i < 6; i++) {
      const date = new Date(monday);
      date.setDate(monday.getDate() + i);
      dates.push({
        date: date.toISOString().split('T')[0],
        dayOfWeek: ['월', '화', '수', '목', '금', '토'][i]
      });
    }

    return dates;
  };

  const fetchDailyWorks = async (dates) => {
    try {
      setLoading(true);
      const data = await getDailyWorks(dates[0].date);
      setDailyData(data);
    } catch (err) {
      console.error('Error getDailyWorks:', err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    const dates = getThisWeekDates(selectedWeekStart);
    setWeekDates(dates);
    fetchDailyWorks(dates);
  }, [selectedWeekStart]);

  const moveWeek = (direction) => {
    const newDate = new Date(selectedWeekStart);
    newDate.setDate(selectedWeekStart.getDate() + (direction * 7));
    setSelectedWeekStart(newDate);
  };

  const createEmptyRows = (existingData = [], rowCount = 5) => {
    const emptyRows = Array(rowCount).fill({ goals: '-', work: '-' });
    return emptyRows.map((empty, index) => 
      existingData[index] || empty
    );
  };

  const handleDateChange = (date) => {
    setSelectedWeekStart(date);
  };

  const handleStartEdit = (index, date, existingWork) => {
    if (existingWork.id) {
      setEditingCell({ index, date });
      setEditValue(existingWork.work || '');
    } else if (!existingWork.work || existingWork.work === '-') {
      setEditingCell({ index, date });
      setEditValue('');
    }
  };

  const handleSaveEdit = async (index, date, workId) => {
    const trimmedValue = editValue.trim();

    try {
      if (workId) {
        if (!trimmedValue) {
          await deleteDailyWork(workId);
        } else {
          await updateDailyWork(workId, {
            work: trimmedValue,
            workDay: date
          });
        }
      } else if (trimmedValue) {
        await createDailyWorks([{
          work: trimmedValue,
          workDay: date
        }]);
      }

      setEditingCell(null);
      setEditValue('');
      const dates = getThisWeekDates(selectedWeekStart);
      await fetchDailyWorks(dates);
    } catch (error) {
      const action = workId 
        ? (!trimmedValue ? '삭제' : '수정')
        : '추가';
      console.error(`Daily Work ${action} 실패:`, error);
      alert(`${action}에 실패했습니다.`);
    }
  };

  const handleStartMonthlyEdit = (index, date, existingGoal) => {
    if (existingGoal.id) {
      setEditingMonthlyGoal({ index, date });
      setEditMonthlyValue(existingGoal.goals || '');
    } else if (!existingGoal.goals || existingGoal.goals === '-') {
      setEditingMonthlyGoal({ index, date });
      setEditMonthlyValue('');
    }
  };

  const handleSaveMonthlyEdit = async (index, date, goalId) => {
    const trimmedValue = editMonthlyValue.trim();

    try {
      if (goalId) {
        if (!trimmedValue) {
          await deleteMonthlyGoal(goalId);
        } else {
          await updateMonthlyGoal(goalId, {
            goals: trimmedValue,
            workDay: date
          });
        }
      } else if (trimmedValue) {
        await createMonthlyGoals([{
          goals: trimmedValue,
          workDay: date
        }]);
      }

      setEditingMonthlyGoal(null);
      setEditMonthlyValue('');
      const dates = getThisWeekDates(selectedWeekStart);
      await fetchDailyWorks(dates);
    } catch (error) {
      const action = goalId 
        ? (!trimmedValue ? '삭제' : '수정')
        : '추가';
      console.error(`Monthly Goal ${action} 실패:`, error);
      alert(`${action}에 실패했습니다.`);
    }
  };

  const handleStartRoutineEdit = (index, date, existingJob) => {
    if (existingJob.id) {
      setEditingRoutineJob({ index, date });
      setEditRoutineValue(existingJob.goals || '');
    } else if (!existingJob.goals || existingJob.goals === '-') {
      setEditingRoutineJob({ index, date });
      setEditRoutineValue('');
    }
  };

  const handleSaveRoutineEdit = async (index, date, jobId) => {
    const trimmedValue = editRoutineValue.trim();

    try {
      if (jobId) {
        if (!trimmedValue) {
          await deleteRoutineJob(jobId);
        } else {
          await updateRoutineJob(jobId, {
            goals: trimmedValue
          });
        }
      } else if (trimmedValue) {
        const currentWeekDates = weekDates.map(d => new Date(d.date));
        const monday = currentWeekDates[0].toISOString().split('T')[0];
        const friday = currentWeekDates[4].toISOString().split('T')[0];

        await createRoutineJobs([{
          goals: trimmedValue,
          workDay: date,
          startedAt: monday,
          endedAt: friday
        }]);
      }

      setEditingRoutineJob(null);
      setEditRoutineValue('');
      const dates = getThisWeekDates(selectedWeekStart);
      await fetchDailyWorks(dates);
    } catch (error) {
      const action = jobId 
        ? (!trimmedValue ? '삭제' : '수정')
        : '추가';
      console.error(`Routine Job ${action} 실패:`, error);
      alert(`${action}에 실패했습니다.`);
    }
  };

  const handleStartProjectEdit = (index, date, existingProject) => {
    if (existingProject.id) {
      setEditingProject({ index, date });
      setEditProjectValue(existingProject.goals || '');
    } else if (!existingProject.goals || existingProject.goals === '-') {
      setEditingProject({ index, date });
      setEditProjectValue('');
    }
  };

  const handleSaveProjectEdit = async (index, date, projectId) => {
    const trimmedValue = editProjectValue.trim();

    try {
      if (projectId) {
        if (!trimmedValue) {
          await deleteProject(projectId);
        } else {
          await updateProject(projectId, {
            goals: trimmedValue
          });
        }
      } else if (trimmedValue) {
        const currentWeekDates = weekDates.map(d => new Date(d.date));
        const monday = currentWeekDates[0].toISOString().split('T')[0];
        const friday = currentWeekDates[4].toISOString().split('T')[0];

        await createProjects([{
          goals: trimmedValue,
          workDay: date,
          startedAt: monday,
          endedAt: friday
        }]);
      }

      setEditingProject(null);
      setEditProjectValue('');
      const dates = getThisWeekDates(selectedWeekStart);
      await fetchDailyWorks(dates);
    } catch (error) {
      const action = projectId 
        ? (!trimmedValue ? '삭제' : '수정')
        : '추가';
      console.error(`Project ${action} 실패:`, error);
      alert(`${action}에 실패했습니다.`);
    }
  };

  return (   //TODO: 컴포넌트로 분리해야하지만 귀찮. 미래의 나야 미안해 뺨 씨게 한대 때리렴
    <>
      <div className="main-container">
        <div className="content">
          <div className="table-header">
            <div className="week-selector">
              <button 
                className="week-nav-button" 
                onClick={() => moveWeek(-1)}
              >
                이전 주
              </button>
              <DatePicker
                selected={selectedWeekStart}
                onChange={handleDateChange}
                locale={ko}
                dateFormat="yyyy-MM-dd"
                className="date-picker"
                customInput={
                  <button className="current-week">
                    {weekDates[0]?.date} ~ {weekDates[5]?.date}
                  </button>
                }
              />
              <button 
                className="week-nav-button" 
                onClick={() => moveWeek(1)}
              >
                다음 주
              </button>
            </div>
            <button 
              className="add-button"
              onClick={() => navigate('/daily-work/new')}
            >
              <span>추가하기</span>
            </button>
          </div>
          <table className="daily-table">
            <colgroup>
              <col style={{width: `${(100/6) * 0.33}%`}} />
              <col span="6" />
            </colgroup>
            <thead>
              <tr>
                <th className="category-header">Cat.</th>
                {weekDates.map((dateInfo) => (
                  <th key={dateInfo.date}>
                    {loading ? '로딩 중...' : `${dateInfo.date} (${dateInfo.dayOfWeek})`}
                  </th>
                ))}
              </tr>
            </thead>
            <tbody>
              <tr>
                <td className="category-cell">Monthly Goal</td>
                {weekDates.map((dateInfo, idx) => (
                  <td key={dateInfo.date}>
                    <ul className="daily-list">
                      {createEmptyRows(dailyData?.[idx]?.monthlyGoals, Math.max(1, dailyData?.[idx]?.monthlyGoals?.length || 0) + 1).map((goal, index) => (
                        <li key={index} className="monthly-goal-item"
                          style={{ height: "max-content"}}>
                          {editingMonthlyGoal?.index === index && editingMonthlyGoal?.date === dateInfo.date ? (
                            <div className="monthly-goal-edit">
                              <input
                                type="text"
                                value={editMonthlyValue}
                                onChange={(e) => setEditMonthlyValue(e.target.value)}
                                placeholder="월간 목표 입력"
                                autoFocus
                                onKeyPress={(e) => {
                                  if (e.key === 'Enter') {
                                    handleSaveMonthlyEdit(index, dateInfo.date, goal.id);
                                  }
                                }}
                                onBlur={() => handleSaveMonthlyEdit(index, dateInfo.date, goal.id)}
                              />
                            </div>
                          ) : (
                            <div 
                              className="monthly-goal-content"
                              onClick={() => handleStartMonthlyEdit(index, dateInfo.date, goal)}
                              style={{ cursor: goal.id || (!goal.goals || goal.goals === '-') ? 'pointer' : 'default' }}
                            >
                              <span className={!goal.id && (!goal.goals || goal.goals === '-') ? 'empty-cell' : 'filled-cell'}>
                                {goal.goals || '-'}
                              </span>
                            </div>
                          )}
                        </li>
                      ))}
                    </ul>
                  </td>
                ))}
              </tr>
              <tr>
                <td className="category-cell">Projects</td>
                {weekDates.map((dateInfo, idx) => (
                  <td key={dateInfo.date}>
                    <ul className="daily-list">
                      {createEmptyRows(dailyData?.[idx]?.projects, Math.max(4, dailyData?.[idx]?.projects?.length || 0) + 1).map((project, index) => (
                        <li key={index} className="project-item"
                          style={{ height: "max-content"}}>
                          {editingProject?.index === index && editingProject?.date === dateInfo.date ? (
                            <div className="project-edit">
                              <input
                                type="text"
                                value={editProjectValue}
                                onChange={(e) => setEditProjectValue(e.target.value)}
                                placeholder="프로젝트 입력"
                                autoFocus
                                onKeyPress={(e) => {
                                  if (e.key === 'Enter') {
                                    handleSaveProjectEdit(index, dateInfo.date, project.id);
                                  }
                                }}
                                onBlur={() => handleSaveProjectEdit(index, dateInfo.date, project.id)}
                              />
                            </div>
                          ) : (
                            <div 
                              className="project-content"
                              onClick={() => handleStartProjectEdit(index, dateInfo.date, project)}
                              style={{ cursor: project.id || (!project.goals || project.goals === '-') ? 'pointer' : 'default' }}
                            >
                              <span className={!project.id && (!project.goals || project.goals === '-') ? 'empty-cell' : 'filled-cell'}>
                                {project.goals || '-'}
                              </span>
                            </div>
                          )}
                        </li>
                      ))}
                    </ul>
                  </td>
                ))}
              </tr>
              <tr>
                <td className="category-cell">Routine Jobs</td>
                {weekDates.map((dateInfo, idx) => (
                  <td key={dateInfo.date}>
                    <ul className="daily-list">
                      {createEmptyRows(dailyData?.[idx]?.routineJobs, Math.max(4, dailyData?.[idx]?.routineJobs?.length || 0) + 1).map((job, index) => (
                        <li key={index} className="routine-job-item"
                          style={{ height: "max-content"}}>
                          {editingRoutineJob?.index === index && editingRoutineJob?.date === dateInfo.date ? (
                            <div className="routine-job-edit">
                              <input
                                type="text"
                                value={editRoutineValue}
                                onChange={(e) => setEditRoutineValue(e.target.value)}
                                placeholder="정기 업무 입력"
                                autoFocus
                                onKeyPress={(e) => {
                                  if (e.key === 'Enter') {
                                    handleSaveRoutineEdit(index, dateInfo.date, job.id);
                                  }
                                }}
                                onBlur={() => handleSaveRoutineEdit(index, dateInfo.date, job.id)}
                              />
                            </div>
                          ) : (
                            <div 
                              className="routine-job-content"
                              onClick={() => handleStartRoutineEdit(index, dateInfo.date, job)}
                              style={{ cursor: job.id || (!job.goals || job.goals === '-') ? 'pointer' : 'default' }}
                            >
                              <span className={!job.id && (!job.goals || job.goals === '-') ? 'empty-cell' : 'filled-cell'}>
                                {job.goals || '-'}
                              </span>
                            </div>
                          )}
                        </li>
                      ))}
                    </ul>
                  </td>
                ))}
              </tr>
              <tr>
                <td className="category-cell">Daily Works</td>
                {weekDates.map((dateInfo, idx) => (
                  <td key={dateInfo.date}>
                    <ul className="daily-list">
                      {createEmptyRows(dailyData?.[idx]?.dailyWorks, Math.max(9, dailyData?.[idx]?.dailyWorks?.length || 0) + 2).map((work, index) => (
                        <li key={index} className="daily-work-item"
                          style={{ height: "max-content"}}>
                          {editingCell?.index === index && editingCell?.date === dateInfo.date ? (
                              <div className="daily-work-edit">
                                <input
                                    type="text"
                                    value={editValue}
                                    onChange={(e) => setEditValue(e.target.value)}
                                    placeholder="작업 내용 입력"
                                    autoFocus
                                    onKeyPress={(e) => {
                                      if (e.key === 'Enter') {
                                        handleSaveEdit(index, dateInfo.date, work.id);
                                      }
                                    }}
                                    onBlur={() => handleSaveEdit(index, dateInfo.date, work.id)}
                                />
                              </div>
                          ) : (
                              <div
                                  className="daily-work-content"
                              onClick={() => handleStartEdit(index, dateInfo.date, work)}
                              style={{ cursor: work.id || (!work.work || work.work === '-') ? 'pointer' : 'default' }}
                            >
                              <span className={!work.id && (!work.work || work.work === '-') ? 'empty-cell' : 'filled-cell'}>
                                {work.work || '-'}
                              </span>
                            </div>
                          )}
                        </li>
                      ))}
                    </ul>
                  </td>
                ))}
              </tr>
            </tbody>
          </table>
        </div>
      </div>

      <AddWorkModal 
        isOpen={isModalOpen} 
        onClose={() => setIsModalOpen(false)}
      />
    </>
  );
}

export default MainPage; 