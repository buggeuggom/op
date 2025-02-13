import { useNavigate, Link } from 'react-router-dom';
import { logout, getStoredAuth } from '../api/authApi';
import '../styles/Navbar.css';

function Navbar() {
  const navigate = useNavigate();
  const auth = getStoredAuth();

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  return (
    <nav className="navbar">
      <div className="navbar-content">
        <div className="navbar-left">
          <Link to="/" className="navbar-brand">
            Daily Work
          </Link>
          <Link to="/admin" className="nav-link">
            관리자
          </Link>
        </div>
        <div className="navbar-user">
          <span className="user-name">
            안녕하세요, {auth?.name}님!
          </span>
          <button 
            onClick={handleLogout}
            className="logout-button"
          >
            로그아웃
          </button>
        </div>
      </div>
    </nav>
  );
}

export default Navbar; 