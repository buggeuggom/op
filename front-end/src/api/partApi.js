import apiClient from './apiClient';

export const getParts = async () => {
  const response = await apiClient.get('/parts');
  return response.data;
}; 