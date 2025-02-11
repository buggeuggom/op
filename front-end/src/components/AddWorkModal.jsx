import { useState } from 'react';
import '../styles/AddWorkModal.css';

function AddWorkModal({ isOpen, onClose }) {
  const [selectedType, setSelectedType] = useState(null);

  const workTypes = [
    { id: 'daily', name: '데일리워크', path: '/add/daily-work' },
    { id: 'routine', name: '루틴잡', path: '/add/routine-job' },
    { id: 'monthly', name: '먼슬리골', path: '/add/monthly-goal' },
    { id: 'project', name: '프로젝트', path: '/add/project' }
  ];

  if (!isOpen) return null;

  return (
    <div className="modal-overlay" onClick={onClose}>
      <div className="modal-content" onClick={e => e.stopPropagation()}>
        <h2>추가하기</h2>
        <div className="work-type-grid">
          {workTypes.map(type => (
            <a 
              key={type.id}
              href={type.path}
              className="work-type-button"
            >
              {type.name}
            </a>
          ))}
        </div>
        <button className="modal-close" onClick={onClose}>닫기</button>
      </div>
    </div>
  );
}

export default AddWorkModal; 