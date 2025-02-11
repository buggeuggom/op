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
      <Link to="/" className="navbar-brand">
        Daily Work
      </Link>
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
    </nav>
  );
}

export default Navbar; 