import { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { signup } from '../api/authApi';
import { getParts } from '../api/partApi';
import '../styles/Auth.css';

function SignupPage() {
  const [formData, setFormData] = useState({
    email: '',
    password: '',
    name: '',
    partName: ''
  });
  const [errors, setErrors] = useState({
    email: '',
    password: ''
  });
  const [parts, setParts] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    // 부서 목록 가져오기
    const fetchParts = async () => {
      try {
        const partsData = await getParts();
        setParts(partsData);
      } catch (error) {
        console.error('부서 정보 가져오기 실패:', error);
        alert('부서 정보를 불러오는데 실패했습니다.');
      }
    };

    fetchParts();
  }, []);

  const validateField = (name, value) => {
    switch (name) {
      case 'email':
        if (value.length < 4) {
          return '아이디는 4글자 이상이어야 합니다.';
        }
        break;
      case 'password':
        if (value.length < 4) {
          return '비밀번호는 4글자 이상이어야 합니다.';
        }
        break;
      default:
        return '';
    }
    return '';
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));

    // 유효성 검사 실행
    const error = validateField(name, value);
    setErrors(prev => ({
      ...prev,
      [name]: error
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    // 모든 필드 유효성 검사
    const emailError = validateField('email', formData.email);
    const passwordError = validateField('password', formData.password);

    if (emailError || passwordError) {
      setErrors({
        email: emailError,
        password: passwordError
      });
      return;
    }

    try {
      await signup(formData);
      navigate('/login');
    } catch (error) {
      console.error('회원가입 실패:', error);
      alert('회원가입에 실패했습니다.');
    }
  };

  return (
    <div className="auth-container">
      <div className="auth-box">
        <h1 className="auth-title">회원가입</h1>
        <form onSubmit={handleSubmit} className="auth-form">
          <div className="form-group">
            <label htmlFor="email">아이디</label>
            <input
              type="text"
              id="email"
              name="email"
              value={formData.email}
              onChange={handleChange}
              required
              minLength={4}
            />
            {errors.email && <span className="error-message">{errors.email}</span>}
          </div>
          <div className="form-group">
            <label htmlFor="password">비밀번호</label>
            <input
              type="password"
              id="password"
              name="password"
              value={formData.password}
              onChange={handleChange}
              required
              minLength={4}
            />
            {errors.password && <span className="error-message">{errors.password}</span>}
          </div>
          <div className="form-group">
            <label htmlFor="name">이름</label>
            <input
              type="text"
              id="name"
              name="name"
              value={formData.name}
              onChange={handleChange}
              required
            />
          </div>
          <div className="form-group">
            <label htmlFor="partName">파트</label>
            <select
              id="partName"
              name="partName"
              value={formData.partName}
              onChange={handleChange}
              required
            >
              <option value="">파트 선택</option>
              {parts.map(part => (
                <option key={part.id} value={part.name}>
                  {part.name}
                </option>
              ))}
            </select>
          </div>
          <button 
            type="submit" 
            className="submit-button"
            disabled={errors.email || errors.password}
          >
            회원가입
          </button>
        </form>
        <div className="auth-link">
          이미 계정이 있으신가요?
          <Link to="/login">로그인</Link>
        </div>
      </div>
    </div>
  );
}

export default SignupPage; 