import React from 'react';
import MonthlyGoalRow from './rows/MonthlyGoalRow';
import ProjectRow from './rows/ProjectRow';
import RoutineJobRow from './rows/RoutineJobRow';
import DailyWorkRow from './rows/DailyWorkRow';

function WorkTable({ weekDates, dailyData, loading, isAdmin = false, ...rowProps }) {
  return (
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
        <MonthlyGoalRow weekDates={weekDates} dailyData={dailyData} isAdmin={isAdmin} {...rowProps} />
        <ProjectRow weekDates={weekDates} dailyData={dailyData} isAdmin={isAdmin} {...rowProps} />
        <RoutineJobRow weekDates={weekDates} dailyData={dailyData} isAdmin={isAdmin} {...rowProps} />
        <DailyWorkRow weekDates={weekDates} dailyData={dailyData} isAdmin={isAdmin} {...rowProps} />
      </tbody>
    </table>
  );
}

export default WorkTable; 