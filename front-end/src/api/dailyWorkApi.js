import apiClient from './apiClient';

const getAuthHeader = () => {
  const auth = JSON.parse(localStorage.getItem('auth'));
  return auth?.token ? { Authorization: auth.token } : {};
};

// 데일리워크 등록
export const createDailyWorks = async (dailyWorks) => {
  await apiClient.post('/daily-works/daily-works', dailyWorks, {
    headers: getAuthHeader()
  });
};

// 프로젝트 등록
export const createProjects = async (projects) => {
  await apiClient.post('/daily-works/projects', projects, {
    headers: getAuthHeader()
  });
};

// 루틴잡 등록
export const createRoutineJobs = async (routineJobs) => {
  await apiClient.post('/daily-works/routine-jobs', routineJobs, {
    headers: getAuthHeader()
  });
};

// 월간목표 등록
export const createMonthlyGoals = async (monthlyGoals) => {
  await apiClient.post('/daily-works/monthly-goals', monthlyGoals, {
    headers: getAuthHeader()
  });
};

// 데일리워크 조회
export const getDailyWorks = async (day) => {
  const response = await apiClient.get('/daily-works', {
    params: { day },
    headers: getAuthHeader()
  });
  return response.data;
};

// 데일리워크 수정
export const updateDailyWork = async (id, dailyWork) => {
  await apiClient.put(`/daily-works/daily-works/${id}`, dailyWork, {
    headers: getAuthHeader()
  });
};

// 데일리워크 삭제
export const deleteDailyWork = async (id) => {
  await apiClient.delete(`/daily-works/daily-works/${id}`, {
    headers: getAuthHeader()
  });
}; 