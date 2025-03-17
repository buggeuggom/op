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

  return saveToLocalStorage(response.data.token, response.data.name);
};

export const signup = async (signupData) => {
  const response = await apiClient.post('/users/signup', signupData);
  return response.data;
};

export const logout = () => {
  localStorage.removeItem('auth');
};

export const getStoredAuth = () => {
  const auth = localStorage.getItem('auth');
  return auth ? JSON.parse(auth) : null;
}; 