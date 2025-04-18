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

