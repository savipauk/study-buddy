import { useEffect, useState } from 'react';
import '../styles/ProfilePage.css';
import Header from '../components/Header';
import { useNavigate } from 'react-router-dom';
import {
  serverFetch,
  getUserByUsername,
  getUserData
} from '../hooks/serverUtils';
import ActiveLesson from '../components/AcitveLesson';
import ActiveGroup from '../components/ActiveGroup';
import { useParams } from 'react-router-dom';

function ProfileInfo() {
  const [userGroups, setUserGroups] = useState([]);
  const [joinedGroups, setJoinedGroups] = useState([]);
  const [showActive, setShowActive] = useState(true);
  const [ownProfile, setOwnProfile] = useState(false);
  const isProfileSetupComplete = JSON.parse(
    localStorage.getItem('isProfileSetupComplete')
  );
  const [role, setRole] = useState('');
  const { username } = useParams();
  const navigate = useNavigate();
  const handleProfileClick = () => {
    navigate('/users/profile/edit');
  };

  const [userInfoForm, setUserInfoForm] = useState({
    FirstName: '',
    LastName: '',
    username: ''
  });

  const [profilePictureUrl, setProfilePictureUrl] = useState('');

  const fetchUserData = async () => {
    const ownProfileCheck = username === localStorage.getItem('user_email');
    setOwnProfile(ownProfileCheck);
    let userData;
    if (ownProfileCheck) {
      userData = await getUserData(localStorage.getItem('user_email'));
    } else {
      userData = await getUserByUsername(username);
    }
    if (userData) {
      setUserInfoForm({
        FirstName: userData.firstName,
        LastName: userData.lastName,
        username: userData.username
      });
      setRole(userData.studyRole);

      const response = await fetch(
        `http://localhost:8080/users/profile-picture/${userData.username}`,
        {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json'
          }
        }
      );
      if (response.ok) {
        const blob = await response.blob();
        if (blob.size > 0) {
          setProfilePictureUrl(URL.createObjectURL(blob));
        } else {
          setProfilePictureUrl(
            'https://static.vecteezy.com/system/resources/previews/005/129/844/non_2x/profile-user-icon-isolated-on-white-background-eps10-free-vector.jpg'
          );
        }
      } else {
        console.error('Greška pri dohvaćanju profilne slike');
      }
    }
  };

  async function getUserGroups(role) {
    console.log(role);
    let endpoint;
    if (role === 'STUDENT') {
      endpoint = `/studyGroup/createdBy/${userInfoForm.username}`;
    } else if (role === 'PROFESSOR') {
      endpoint = `/lesson/createdBy/${userInfoForm.username}`;
    }
    const options = {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json'
      }
    };
    try {
      const response = await serverFetch(endpoint, options);
      if (response.ok) {
        const data = await response.json();
        setUserGroups(data);
      } else {
        console.log('response error');
      }
    } catch (error) {
      console.log(error);
    }
  }

  async function getUserJoinedGroups(role) {
    let endpoint;
    if (role === 'STUDENT') {
      endpoint = `/studyGroup/active/${userInfoForm.username}`;
    } else if (role === 'PROFESSOR') {
      endpoint = `/lesson/active/${userInfoForm.username}`;
    }
    const options = {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json'
      }
    };
    try {
      const response = await serverFetch(endpoint, options);
      if (response.ok) {
        const data = await response.json();
        setJoinedGroups(data);
      } else {
        console.log('response error');
      }
    } catch (error) {
      console.log(error);
    }
  }

  useEffect(() => {
    const fetchData = async () => {
      if (!isProfileSetupComplete) {
        navigate('/users/home');
        return;
      } else {
        try {
          await fetchUserData();
        } catch (error) {
          console.log('Error while fetching', error);
        }
      }
    };
    fetchData();
  }, [isProfileSetupComplete, navigate]);

  useEffect(() => {
    const fetchGroups = async () => {
      if (role) {
        await getUserGroups(role);
        await getUserJoinedGroups(role);
      }
    };
    fetchGroups();
  }, [role]);

  return (
    <div>
      <Header />
      <div className="profileWrapper">
        <div className="profileInfoWrapper">
          <div className="profilePictureInfo">
            <img src={profilePictureUrl} className="image"></img>
          </div>
          <div className="accountInfo">
            <div className="username">
              <label>{userInfoForm.FirstName}</label>
            </div>
            <div className="username">
              <label>{userInfoForm.LastName}</label>
            </div>
          </div>
          {ownProfile && (
            <button className="editProfileButton" onClick={handleProfileClick}>
              <i className="fa-solid fa-user-pen"></i>
            </button>
          )}
        </div>
        <div className="groupHistory">
          <label className="groupHistoryTitle">Popis STUDY GROUP-a</label>
        </div>
        {ownProfile && (
          <div className="groupChoice">
            <button onClick={() => setShowActive(true)}>Kreirane grupe</button>
            <button onClick={() => setShowActive(false)}>
              Pridruzene grupe
            </button>
          </div>
        )}
        {showActive && (
          <div className="userGroups">
            {userGroups.length === 0 ? (
              <p>Korisnik nije kreirao niti jednu grupu</p>
            ) : (
              userGroups.map((item, index) =>
                role === 'STUDENT' ? (
                  <ActiveGroup key={index} group={item} />
                ) : role === 'PROFESSOR' ? (
                  <ActiveLesson key={index} lesson={item} />
                ) : null
              )
            )}
          </div>
        )}
        {!showActive && (
          <div className="userGroups">
            {joinedGroups.length === 0 && !showActive ? (
              <p>Korisnik pridruzen niti jednoj grupi</p>
            ) : (
              joinedGroups.map((item, index) =>
                role === 'STUDENT' ? (
                  <ActiveGroup key={index} group={item} />
                ) : role === 'PROFESSOR' ? (
                  <ActiveLesson key={index} lesson={item} />
                ) : null
              )
            )}
          </div>
        )}
      </div>
    </div>
  );
}

export default ProfileInfo;
