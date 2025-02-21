import React from 'react';
import DatePicker from 'react-datepicker';
import { ko } from 'date-fns/locale';

function WeekSelector({ selectedWeekStart, weekDates, onDateChange, onMoveWeek }) {
  return (
    <div className="week-selector">
      <button 
        className="week-nav-button" 
        onClick={() => onMoveWeek(-1)}
      >
        이전 주
      </button>
      <DatePicker
        selected={selectedWeekStart}
        onChange={onDateChange}
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
        onClick={() => onMoveWeek(1)}
      >
        다음 주
      </button>
    </div>
  );
}

export default WeekSelector; 