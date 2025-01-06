import { useEffect, useState } from 'react';
import '../styles/ProfilePage.css';
import Header from '../components/Header';
import { useNavigate } from 'react-router-dom';
import useAuth from '../hooks/useAuth';
import { getUserData } from '../hooks/serverUtils';

function ProfileInfo() {
  const { isProfileSetupComplete } = useAuth();
  const navigate = useNavigate();
  const handleProfileClick = () => {
    navigate('/users/profile/edit');
  };
  const [userInfoForm, setUserInfoForm] = useState({
    FirstName: '',
    LastName: ''
  });

  const fetchUserData = async () => {
    const userEmail = localStorage.getItem('user_email');
    const userData = await getUserData(userEmail);
    if (userData) {
      setUserInfoForm({
        FirstName: userData.firstName,
        LastName: userData.lastName
      });
    }
  };

  useEffect(() => {
    if (!isProfileSetupComplete) {
      navigate('/users/home');
    } else {
      fetchUserData();
    }
  }, [isProfileSetupComplete, navigate]);

  return (
      <div>
        <Header />
        <div className="profileWrapper">
          <div className="profileInfoWrapper">
            <div className="profilePictureInfo">
              <img
                  src="https://static.vecteezy.com/system/resources/previews/005/129/844/non_2x/profile-user-icon-isolated-on-white-background-eps10-free-vector.jpg"
                  className="image"
              ></img>
            </div>
            <div className="accountInfo">
              <div className="username">
                <label>{userInfoForm.FirstName}</label>
              </div>
              <div className="username">
                <label>{userInfoForm.LastName}</label>
              </div>
            </div>
            <button className="editProfileButton" onClick={handleProfileClick}>
              <i className="fa-solid fa-user-pen"></i>
            </button>
          </div>
          <div className="groupHistory">
            <label className="groupHistoryTitle">Popis STUDY GROUP-a</label>
          </div>
        </div>
      </div>
  );
}
export default ProfileInfo;
