import '../styles/Root.css';
import { useNavigate } from 'react-router-dom';
import useAuth from '../hooks/useAuth';

function Header() {
  const navigate = useNavigate();

  const { signOut } = useAuth();
  const handleClickProfile = () => {
    navigate('/users/profile');
  };
  const handleClickHome = () => {
    navigate('/users/home');
  };
  const handleClickSignOut = () => {
    signOut();
    navigate('/');
  };

  return (
    <header className="header">
      <div className="headerSide">
        <button className="headerButtons" onClick={handleClickHome}>
          <i className="fa-solid fa-house"></i>
        </button>
      </div>
      <h1 className="headerText">Study Buddy</h1>
      <div className="headerSide">
        <button className="headerButtons" onClick={handleClickProfile}>
          <i className="fa-solid fa-user"></i>
        </button>
        <button className="headerButtonSignOut" onClick={handleClickSignOut}>
          <i className="fa-solid fa-arrow-right-from-bracket"></i>
        </button>
      </div>
    </header>
  );
}

export default Header;
