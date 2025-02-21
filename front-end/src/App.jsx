import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import ProtectedRoute from './components/ProtectedRoute';
import Navbar from './components/Navbar';
import MainPage from './pages/MainPage';
import AdminPage from './pages/AdminPage';
import LoginPage from './pages/LoginPage';
import SignupPage from './pages/SignupPage';
import AddWorkPage from './pages/AddWorkPage';
import './styles/global.css';
import './App.css'

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/login" element={<LoginPage />} />
        <Route path="/signup" element={<SignupPage />} />
        <Route
          path="/"
          element={
            <ProtectedRoute>
              <>
                <Navbar />
                <MainPage />
              </>
            </ProtectedRoute>
          }
        />
        <Route
          path="/admin"
          element={
            <ProtectedRoute>
              <>
                <Navbar />
                <AdminPage />
              </>
            </ProtectedRoute>
          }
        />
        <Route
          path="/daily-work/new"
          element={
            <ProtectedRoute>
              <>
                <Navbar />
                <AddWorkPage />
              </>
            </ProtectedRoute>
          }
        />
      </Routes>
    </Router>
  );
}

export default App