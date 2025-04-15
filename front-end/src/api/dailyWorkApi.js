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

// Monthly Goals
export const createMonthlyGoals = async (monthlyGoals) => {
  await apiClient.post('/daily-works/monthly-goals', monthlyGoals, {
    headers: getAuthHeader()
  });
};

export const updateMonthlyGoal = async (id, monthlyGoal) => {
  await apiClient.put(`/daily-works/monthly-goals/${id}`, monthlyGoal, {
    headers: getAuthHeader()
  });
};

export const deleteMonthlyGoal = async (id) => {
  await apiClient.delete(`/daily-works/monthly-goals/${id}`, {
    headers: getAuthHeader()
  });
};

// Routine Jobs
export const createRoutineJobs = async (routineJobs) => {
  await apiClient.post('/daily-works/routine-jobs', routineJobs, {
    headers: getAuthHeader()
  });
};

export const updateRoutineJob = async (id, routineJob) => {
  await apiClient.put(`/daily-works/routine-jobs/${id}`, routineJob, {
    headers: getAuthHeader()
  });
};

export const deleteRoutineJob = async (id) => {
  await apiClient.delete(`/daily-works/routine-jobs/${id}`, {
    headers: getAuthHeader()
  });
};

// Projects
export const createProjects = async (projects) => {
  await apiClient.post('/daily-works/projects', projects, {
    headers: getAuthHeader()
  });
};

export const updateProject = async (id, project) => {
  await apiClient.put(`/daily-works/projects/${id}`, project, {
    headers: getAuthHeader()
  });
};

export const deleteProject = async (id) => {
  await apiClient.delete(`/daily-works/projects/${id}`, {
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

export const getDailyWorkAdmin = async (day, email) => {
  const response = await apiClient.get('/daily-works/admin', {
    params: { day, email },
    headers: getAuthHeader()
  });
  return response.data;
};

export const getDailyWorkJSONAdmin = async (day, email) => {
  const response = await apiClient.get('/daily-works/admin/json', {
    params: { day, email },
    headers: getAuthHeader()
  });
  return response.data;
};

export const getDailyWorkJSON = async () => {
  const response = await apiClient.get('/daily-works/my/json', {
    headers: getAuthHeader()
  });
  return response.data;
};