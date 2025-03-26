import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { createProjects, createRoutineJobs } from '../api/dailyWorkApi';
import ProjectForm from '../components/ProjectForm';
import RoutineJobForm from '../components/RoutineJobForm';
import '../styles/AddWorkPage.css';

function AddWorkPage() {
  const navigate = useNavigate();
  const today = new Date().toISOString().split('T')[0];
  
  const [formData, setFormData] = useState({
    projects: [{ goals: '', startedAt: '', endedAt: '' }],
    routineJobs: [{ goals: '', startedAt: '', endedAt: '' }]
  });

  const handleItemChange = (type, index, field, value) => {
    setFormData(prev => ({
      ...prev,
      [type]: prev[type].map((item, i) => 
        i === index ? { ...item, [field]: value } : item
      )
    }));
  };

  const addItem = (type) => {
    const newItem = { goals: '', startedAt: '', endedAt: '' };
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

      await Promise.all(promises);
      navigate('/');
    } catch (error) {
      console.error('작업 추가 실패:', error);
      alert('작업 추가에 실패했습니다.');
    }
  };

  return (
    <>
      <div className="add-work-container">
        <div className="add-work-content">
          <h1>새로운 근무 추가</h1>
          <form onSubmit={handleSubmit} className="add-work-form">
            <div className="form-row">
              <ProjectForm 
                projects={formData.projects}
                onItemChange={handleItemChange}
                onAddItem={addItem}
                onRemoveItem={removeItem}
              />

              <RoutineJobForm 
                routineJobs={formData.routineJobs}
                onItemChange={handleItemChange}
                onAddItem={addItem}
                onRemoveItem={removeItem}
              />
            </div>

            <div className="button-group">
              <button type="button" className="submit-button" style={{backgroundColor:'gray'}} onClick={() => navigate('/')}>
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