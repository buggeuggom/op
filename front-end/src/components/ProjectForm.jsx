import React from 'react';

function ProjectForm({ projects, onItemChange, onAddItem, onRemoveItem }) {
  return (
    <div className="form-section">
      <h2>프로젝트</h2>
      {projects.map((project, index) => (
        <div key={index} className="form-group">
          <div className="input-group">
            <input
              type="text"
              value={project.goals}
              onChange={(e) => onItemChange('projects', index, 'goals', e.target.value)}
              placeholder="프로젝트 내용"
            />
            <div className="date-inputs">
              <label>시작일</label>
              <input
                type="date"
                value={project.startedAt}
                onChange={(e) => onItemChange('projects', index, 'startedAt', e.target.value)}
              />
              <label>종료일</label>
              <input
                type="date"
                value={project.endedAt}
                onChange={(e) => onItemChange('projects', index, 'endedAt', e.target.value)}
              />
            </div>
            {projects.length > 1 && (
              <button 
                type="button" 
                className="remove-button"
                onClick={() => onRemoveItem('projects', index)}
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
        onClick={() => onAddItem('projects')}
      >
        + 추가
      </button>
    </div>
  );
}

export default ProjectForm; 