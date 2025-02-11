import React, { useState, useEffect } from 'react';
import apiClient from '../api/apiClient';

function ExampleComponent() {
  const [data, setData] = useState(null);

  // GET 요청 예시
  const fetchData = async () => {
    try {
      const response = await apiClient.get('/api/example');
      setData(response.data);
    } catch (error) {
      console.error('데이터 조회 실패:', error);
    }
  };

  // POST 요청 예시
  const sendData = async (data) => {
    try {
      const response = await apiClient.post('/api/example', data);
      console.log('전송 성공:', response.data);
    } catch (error) {
      console.error('전송 실패:', error);
    }
  };

  useEffect(() => {
    fetchData();
  }, []);

  return (
    <div>
      {/* 컴포넌트 내용 */}
    </div>
  );
}

export default ExampleComponent; 