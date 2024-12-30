import { useEffect } from 'react';
import '../styles/ProfilePage.css';
import Header from '../components/Header';
import { useNavigate } from 'react-router-dom';
import useAuth from '../hooks/useAuth';

function ProfileInfo() {
  const { isProfileSetupComplete } = useAuth();
  const navigate = useNavigate();
  const handleProfileClick = () => {
    navigate('/users/profile/edit');
  };

  useEffect(() => {
    if (!isProfileSetupComplete) {
      navigate('/users/home');
    }
  }, [isProfileSetupComplete, navigate]);

  return (
    <div>
      <Header />
      <div className="profileInfoWrapper">
        <div className="profilePictureInfo">
          <img
            src="https://static.vecteezy.com/system/resources/previews/005/129/844/non_2x/profile-user-icon-isolated-on-white-background-eps10-free-vector.jpg"
            className="image"
          ></img>
        </div>
        <div className="accountInfo">
          <div className="username">
            <label>First Name</label>
          </div>
          <div className="username">
            <label>Last Name</label>
          </div>
        </div>
        <button className="editProfileButton" onClick={handleProfileClick}>
          <i className="fa-solid fa-user-pen"></i>
        </button>
      </div>
      <div className="groupHistory">
        <label className="groupHistoryTitle">StudyGroup history</label>
      </div>
    </div>
  );
}
export default ProfileInfo;
