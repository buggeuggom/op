import { Navigate } from 'react-router-dom';
import { getStoredAuth } from '../api/authApi';

function ProtectedRoute({ children }) {
  const auth = getStoredAuth();
  
  if (!auth) {
    return <Navigate to="/login" replace />;
  }
  
  return children;
}

export default ProtectedRoute; 