import axios from "axios";

const apiClient = axios.create({
  baseURL: "/api",
  headers: {
    "Content-Type": "application/json",
  },
});

// 토큰을 요청 헤더에 추가하는 Interceptor
apiClient.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");
  if (token) {
    config.headers.Authorization = `${token}`;
  }
  return config;
});

// 401 응답 처리 Interceptor
apiClient.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      localStorage.removeItem("token");
      window.location.href = "/login";
    }
    return Promise.reject(error);
  }
);

export default {
  signup(data) {
    return apiClient.post("/users/signup", data);
  },
  login(data) {
    return apiClient.post("/users/login", data);
  },
  getAbnormalities() {
    return apiClient.get("/monitoringabnormalities");
  },
  createAbnormality(data) {
    return apiClient.post("/monitoringabnormalities", data);
  },
  getWorkList(){
    return apiClient.get("/works");
  },
  creatWork(data){
    return apiClient.post("works", data);
  }
};

