import { useState, useEffect, useContext } from 'react';
import DatePicker from 'react-datepicker';
import "react-datepicker/dist/react-datepicker.css";
import { ko } from 'date-fns/locale'; 
import { getDailyWorkAdmin } from '../api/userApi';
import { getUsers } from '../api/userApi';
import '../styles/MainPage.css';
import '../styles/AdminPage.css';
import { useNavigate } from 'react-router-dom';
import { getStoredAuth } from '../api/authApi';

function AdminPage() {
  const [dailyData, setDailyData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [weekDates, setWeekDates] = useState([]);
  const [selectedWeekStart, setSelectedWeekStart] = useState(new Date());
  const [users, setUsers] = useState([]);
  const [selectedUser, setSelectedUser] = useState('');
  const navigate = useNavigate();
  const auth = getStoredAuth();

  const getThisWeekDates = (baseDate) => {
    // baseDate를 한국 시간(UTC+9) 기준으로 변환
    const koreaDate = new Date(baseDate.getTime() + 9 * 60 * 60 * 1000);
    koreaDate.setUTCHours(0, 0, 0, 0); // 시간을 00:00:00.000으로 설정
    
    const currentDay = koreaDate.getUTCDay(); // UTC 기준 요일 (일요일: 0, 월요일: 1, ..., 토요일: 6)

    // 월요일을 기준으로 주 시작일 계산 (일요일이면 월요일로 보정)
    const monday = new Date(koreaDate);
    monday.setUTCDate(koreaDate.getUTCDate() - (currentDay === 0 ? 6 : currentDay - 1));

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

  useEffect(() => {
    // 사용자 목록 가져오기
    const fetchUsers = async () => {
      try {
        const data = await getUsers();
        setUsers(data);
      } catch (error) {
        console.error('Error fetching users:', error);
      }
    };
    fetchUsers();
  }, []);

  const downloadUserDataAsJson = (userData, userName) => {
    // JSON 데이터를 문자열로 변환
    const jsonString = JSON.stringify(userData, null, 2);
    
    console.log(jsonString);
    // Blob 객체 생성
    const blob = new Blob([jsonString], { type: 'application/json' });
    
    // 다운로드 링크 생성
    const url = window.URL.createObjectURL(blob);
    const link = document.createElement('a');
    link.href = url;
    link.download = `${userName}.json`; // 파일명 설정
    
    // 링크 클릭 시뮬레이션
    document.body.appendChild(link);
    link.click();
    
    // cleanup
    document.body.removeChild(link);
    window.URL.revokeObjectURL(url);
  };

  const handleDataFetch = async (email, name) => {
    if (!email || !name) {
      alert('사용자를 선택해주세요');
      return;
    }

    try {
      const response = await getDailyWorkAdmin(weekDates[0].date, email);
      if (response) {
        // 데이터를 JSON 파일로 다운로드
        downloadUserDataAsJson(response, name);
      }
    } catch (error) {
      console.error('데이터 가져오기 실패:', error);
    }
  };

  const fetchDailyWorks = async (dates, email) => {
    if (!email) return;
    
    try {
      setLoading(true);
      const data = await getDailyWorkAdmin(dates[0].date, email);
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
    if (selectedUser) {
      fetchDailyWorks(dates, selectedUser);
    }
  }, [selectedWeekStart, selectedUser]);

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

  const handleUserSelect = (email) => {
    setSelectedUser(email);
  };

  return (
    <>
      <div className="main-container">
        <div className="content">
          <div className="admin-header">
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
            <div className="header-right">
              <div className="user-search">
                <select 
                  value={selectedUser}
                  onChange={(e) => handleUserSelect(e.target.value)}
                  className="user-select"
                >
                  <option value="">사용자 선택</option>
                  {users.map(user => (
                    <option key={user.email} value={user.email}>
                      {user.name}
                    </option>
                  ))}
                </select>
              </div>
              {selectedUser && auth?.name === '조기영' && (
                <button 
                  onClick={() => handleDataFetch(selectedUser, users.find(u => u.email === selectedUser)?.name)}
                  className="btn btn-primary btn-sm"
                >
                  JSON 다운로드
                </button>
              )}
            </div>
          </div>
          {selectedUser ? (
            <table className="daily-table">
              <colgroup>
                <col style={{width: `${(100/6) * 0.33}%`}} /> 
                <col span="6" /> {/* 나머지 6개 열 */}
              </colgroup>
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
                          <li key={index}
                          style={{ height: "max-content"}}>
                            {project.goals || '-'}</li>
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
                          <li key={index}
                          style={{ height: "max-content"}}>{job.goals || '-'}</li>
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
                        {createEmptyRows(dailyData?.[idx]?.dailyWorks, 10).map((work, index) => (
                          <li key={index} style={{ height: "max-content"}}>{work.work || '-'}</li>
                        ))}
                      </ul>
                    </td>
                  ))}
                </tr>
              </tbody>
            </table>
          ) : (
            <div className="no-user-selected">
              사용자를 선택해주세요
            </div>
          )}
        </div>
      </div>
    </>
  );
}

export default AdminPage; 