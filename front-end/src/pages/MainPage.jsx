import { useState, useEffect } from 'react';
import DatePicker from 'react-datepicker';
import "react-datepicker/dist/react-datepicker.css";
import { ko } from 'date-fns/locale'; 
import Navbar from '../components/Navbar';
import AddWorkModal from '../components/AddWorkModal';
import { getDailyWorks, createDailyWorks, updateDailyWork, deleteDailyWork } from '../api/dailyWorkApi';
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
  const navigate = useNavigate();

  const getThisWeekDates = (baseDate) => {
    const koreaDate = new Date(baseDate.getTime());
    const currentDay = koreaDate.getDay();
    
    const monday = new Date(koreaDate);
    monday.setDate(koreaDate.getDate() - (currentDay === 0 ? 6 : currentDay - 1));
    
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
      console.error('Error fetching daily works:', err);
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

  return (
    <>
      <Navbar />
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
            <thead>
              <tr>
                <th>Category</th>
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
                      {createEmptyRows(dailyData?.[idx]?.monthlyGoals, 2).map((goal, index) => (
                        <li key={index}>{goal.goals || '-'}</li>
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
                      {createEmptyRows(dailyData?.[idx]?.projects, 5).map((project, index) => (
                        <li key={index}>{project.goals || '-'}</li>
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
                      {createEmptyRows(dailyData?.[idx]?.routineJobs, 5).map((job, index) => (
                        <li key={index}>{job.goals || '-'}</li>
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
                        <li key={index} className="daily-work-item">
                          {editingCell?.index === index && editingCell?.date === dateInfo.date ? (
                            <div className="daily-work-edit">
                              <input
                                type="text"
                                value={editValue}
                                onChange={(e) => setEditValue(e.target.value)}
                                placeholder="작업 내용 입력 (비우면 삭제)"
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