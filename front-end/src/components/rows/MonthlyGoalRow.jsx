import React from 'react';

function MonthlyGoalRow({ weekDates, dailyData, isAdmin, createEmptyRows, ...editProps }) {
  return (
    <tr>
      <td className="category-cell">Monthly Goal</td>
      {weekDates.map((dateInfo, idx) => (
        <td key={dateInfo.date}>
          <ul className="daily-list">
            {createEmptyRows(dailyData?.[idx]?.monthlyGoals, Math.max(1, dailyData?.[idx]?.monthlyGoals?.length || 0) + 1).map((goal, index) => (
              <li key={index} className="monthly-goal-item">
                {isAdmin ? (
                  <span>{goal.goals || '-'}</span>
                ) : (
                  // 여기에 기존의 편집 가능한 월간 목표 컴포넌트 내용
                  // ... 기존 코드 ...
                )}
              </li>
            ))}
          </ul>
        </td>
      ))}
    </tr>
  );
}

export default MonthlyGoalRow; 