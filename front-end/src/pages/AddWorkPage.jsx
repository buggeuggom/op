import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Navbar from '../components/Navbar';
import { 
  createMonthlyGoals, 
  createProjects, 
  createRoutineJobs, 
  createDailyWorks 
} from '../api/dailyWorkApi';
import '../styles/AddWorkPage.css';

function AddWorkPage() {
  const navigate = useNavigate();
  const today = new Date().toISOString().split('T')[0];
  
  const [formData, setFormData] = useState({
    monthlyGoals: [{ workDay: today, goals: '' }],
    projects: [{ goals: '', startedAt: '', endedAt: '' }],
    routineJobs: [{ goals: '', startedAt: '', endedAt: '' }],
    dailyWorks: [{ work: '', workDay: today }]
  });

  const handleMonthlyGoalChange = (index, field, value) => {
    setFormData(prev => ({
      ...prev,
      monthlyGoals: prev.monthlyGoals.map((goal, i) => 
        i === index ? { ...goal, [field]: value } : goal
      )
    }));
  };

  const addMonthlyGoal = () => {
    setFormData(prev => ({
      ...prev,
      monthlyGoals: [
        ...prev.monthlyGoals,
        { workDay: new Date().toISOString().split('T')[0], goals: '' }
      ]
    }));
  };

  const removeMonthlyGoal = (index) => {
    setFormData(prev => ({
      ...prev,
      monthlyGoals: prev.monthlyGoals.filter((_, i) => i !== index)
    }));
  };

  const handleItemChange = (type, index, field, value) => {
    setFormData(prev => ({
      ...prev,
      [type]: prev[type].map((item, i) => 
        i === index ? { ...item, [field]: value } : item
      )
    }));
  };

  const addItem = (type) => {
    const newItem = type === 'dailyWorks' 
      ? { work: '', workDay: today }
      : { goals: '', startedAt: '', endedAt: '' };

    setFormData(prev => ({
      ...prev,
      [type]: [...prev[type], newItem]
    }));
  };

  const removeItem = (type, index) => {
    setFormData(prev => ({
      ...prev,
      [type]: prev[type].filter((_, i) => i !== index)
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const promises = [];
      
      if (formData.monthlyGoals.some(goal => goal.goals)) {
        const monthlyGoalRequests = formData.monthlyGoals
          .filter(goal => goal.goals)
          .map(goal => ({
            workDay: goal.workDay,
            goals: goal.goals
          }));
        promises.push(createMonthlyGoals(monthlyGoalRequests));
      }

      if (formData.projects.some(proj => proj.goals)) {
        const projectRequests = formData.projects
          .filter(proj => proj.goals)
          .map(proj => ({
            goals: proj.goals,
            startedAt: proj.startedAt,
            endedAt: proj.endedAt
          }));
        promises.push(createProjects(projectRequests));
      }

      if (formData.routineJobs.some(job => job.goals)) {
        const routineJobRequests = formData.routineJobs
          .filter(job => job.goals)
          .map(job => ({
            goals: job.goals,
            startedAt: job.startedAt,
            endedAt: job.endedAt
          }));
        promises.push(createRoutineJobs(routineJobRequests));
      }

      if (formData.dailyWorks.some(work => work.work)) {
        const dailyWorkRequests = formData.dailyWorks
          .filter(work => work.work)
          .map(work => ({
            work: work.work,
            workDay: work.workDay
          }));
        promises.push(createDailyWorks(dailyWorkRequests));
      }

      await Promise.all(promises);
      navigate('/');
    } catch (error) {
      console.error('작업 추가 실패:', error);
      alert('작업 추가에 실패했습니다.');
    }
  };

  return (
    <>
      <Navbar />
      <div className="add-work-container">
        <div className="add-work-content">
          <h1>새로운 근무 추가</h1>
          <form onSubmit={handleSubmit} className="add-work-form">
            <div className="form-section">
              <h2>월간 목표</h2>
              {formData.monthlyGoals.map((goal, index) => (
                <div key={index} className="form-group monthly-goal-item">
                  <div className="monthly-goal-inputs">
                    <input
                      type="date"
                      value={goal.workDay}
                      onChange={(e) => handleMonthlyGoalChange(index, 'workDay', e.target.value)}
                    />
                    <input
                      type="text"
                      value={goal.goals}
                      onChange={(e) => handleMonthlyGoalChange(index, 'goals', e.target.value)}
                      placeholder="월간 목표 내용"
                    />
                    {formData.monthlyGoals.length > 1 && (
                      <button 
                        type="button" 
                        className="remove-button"
                        onClick={() => removeMonthlyGoal(index)}
                      >
                        삭제
                      </button>
                    )}
                  </div>
                </div>
              ))}
              <button 
                type="button" 
                className="add-button"
                onClick={addMonthlyGoal}
              >
                + 추가
              </button>
            </div>

            <div className="form-section">
              <h2>프로젝트</h2>
              {formData.projects.map((project, index) => (
                <div key={index} className="form-group">
                  <div className="input-group">
                    <input
                      type="text"
                      value={project.goals}
                      onChange={(e) => handleItemChange('projects', index, 'goals', e.target.value)}
                      placeholder="프로젝트 내용"
                    />
                    <div className="date-inputs">
                      <label>시작일</label>
                      <input
                        type="date"
                        value={project.startedAt}
                        onChange={(e) => handleItemChange('projects', index, 'startedAt', e.target.value)}
                      />
                      <label>종료일</label>
                      <input
                        type="date"
                        value={project.endedAt}
                        onChange={(e) => handleItemChange('projects', index, 'endedAt', e.target.value)}
                      />
                    </div>
                    {formData.projects.length > 1 && (
                      <button 
                        type="button" 
                        className="remove-button"
                        onClick={() => removeItem('projects', index)}
                      >
                        삭제
                      </button>
                    )}
                  </div>
                </div>
              ))}
              <button 
                type="button" 
                className="add-button"
                onClick={() => addItem('projects')}
              >
                + 추가
              </button>
            </div>

            <div className="form-section">
              <h2>정기 업무</h2>
              {formData.routineJobs.map((job, index) => (
                <div key={index} className="form-group">
                  <div className="input-group">
                    <input
                      type="text"
                      value={job.goals}
                      onChange={(e) => handleItemChange('routineJobs', index, 'goals', e.target.value)}
                      placeholder="업무 내용"
                    />
                    <div className="date-inputs">
                      <label>시작일</label>
                      <input
                        type="date"
                        value={job.startedAt}
                        onChange={(e) => handleItemChange('routineJobs', index, 'startedAt', e.target.value)}
                      />
                      <label>종료일</label>
                      <input
                        type="date"
                        value={job.endedAt}
                        onChange={(e) => handleItemChange('routineJobs', index, 'endedAt', e.target.value)}
                      />
                    </div>
                    {formData.routineJobs.length > 1 && (
                      <button 
                        type="button" 
                        className="remove-button"
                        onClick={() => removeItem('routineJobs', index)}
                      >
                        삭제
                      </button>
                    )}
                  </div>
                </div>
              ))}
              <button 
                type="button" 
                className="add-button"
                onClick={() => addItem('routineJobs')}
              >
                + 추가
              </button>
            </div>

            <div className="form-section">
              <h2>오늘 할 일</h2>
              {formData.dailyWorks.map((work, index) => (
                <div key={index} className="form-group">
                  <div className="input-group">
                    <div className="daily-work-inputs">
                      <input
                        type="date"
                        value={work.workDay}
                        onChange={(e) => handleItemChange('dailyWorks', index, 'workDay', e.target.value)}
                      />
                      <input
                        type="text"
                        value={work.work}
                        onChange={(e) => handleItemChange('dailyWorks', index, 'work', e.target.value)}
                        placeholder="클릭하여 오늘 할 일 추가"
                      />
                    </div>
                    {formData.dailyWorks.length > 1 && (
                      <button 
                        type="button" 
                        className="remove-button"
                        onClick={() => removeItem('dailyWorks', index)}
                      >
                        삭제
                      </button>
                    )}
                  </div>
                </div>
              ))}
              <button 
                type="button" 
                className="add-button"
                onClick={() => addItem('dailyWorks')}
              >
                + 추가
              </button>
            </div>

            <div className="button-group">
              <button type="button" className="cancel-button" onClick={() => navigate('/')}>
                취소
              </button>
              <button type="submit" className="submit-button">
                저장
              </button>
            </div>
          </form>
        </div>
      </div>
    </>
  );
}

export default AddWorkPage; 