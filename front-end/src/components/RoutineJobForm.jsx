import React from 'react';

function RoutineJobForm({ routineJobs, onItemChange, onAddItem, onRemoveItem }) {
  return (
    <div className="form-section">
      <h2>정기 업무</h2>
      {routineJobs.map((job, index) => (
        <div key={index} className="form-group">
          <div className="input-group">
            <input
              type="text"
              value={job.goals}
              onChange={(e) => onItemChange('routineJobs', index, 'goals', e.target.value)}
              placeholder="업무 내용"
            />
            <div className="date-inputs">
              <label>시작일</label>
              <input
                type="date"
                value={job.startedAt}
                onChange={(e) => onItemChange('routineJobs', index, 'startedAt', e.target.value)}
              />
              <label>종료일</label>
              <input
                type="date"
                value={job.endedAt}
                onChange={(e) => onItemChange('routineJobs', index, 'endedAt', e.target.value)}
              />
            </div>
            {routineJobs.length > 1 && (
              <button 
                type="button" 
                className="remove-button"
                onClick={() => onRemoveItem('routineJobs', index)}
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
        onClick={() => onAddItem('routineJobs')}
      >
        + 추가
      </button>
    </div>
  );
}

export default RoutineJobForm; 