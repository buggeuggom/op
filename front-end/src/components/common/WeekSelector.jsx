import React from 'react';
import DatePicker from 'react-datepicker';
import { ko } from 'date-fns/locale';

function WeekSelector({ selectedWeekStart, weekDates, onDateChange, onMoveWeek }) {  //TODO: 처음엔 쓰겠다고 만들었으나 까먹고 사용하지 않은 비운의 파일
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