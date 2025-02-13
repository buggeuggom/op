import apiClient from './apiClient';


const getAuthHeader = () => {
  const auth = JSON.parse(localStorage.getItem('auth'));
  return auth?.token ? { Authorization: auth.token } : {};
};

export const getUsers = async (role) => {
  const response = await apiClient.get(`/users`, {
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