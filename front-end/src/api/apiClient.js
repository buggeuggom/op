import axios from 'axios';

// axios 인스턴스 생성
const apiClient = axios.create({
  baseURL: '/api',
  headers: {
    'Content-Type': 'application/json',
  },
});

export default apiClient; 