import apiClient from './apiClient';

const saveToLocalStorage = (token, name) => {
  const authData = { token, name };
  localStorage.setItem('auth', JSON.stringify(authData));
  return authData;
};

export const login = async (email, password) => {
  const response = await apiClient.post('/users/login', {
    email,
    password
  });
  // 로그인 성공 시 로컬 스토리지에 저장
  return saveToLocalStorage(response.data.token, response.data.name);
};

export const signup = async (email, password, name) => {
  const response = await apiClient.post('/users/signup', {
    email,
    password,
    name
  });
  return response.data;
};

export const logout = () => {
  localStorage.removeItem('auth');
};

export const getStoredAuth = () => {
  const auth = localStorage.getItem('auth');
  return auth ? JSON.parse(auth) : null;
}; 