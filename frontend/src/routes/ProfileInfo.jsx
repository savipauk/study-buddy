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
  const [userJoinedGroups, setUserJoinedGroups] = useState([]);
  const [joinedGroups, setJoinedGroups] = useState([]);
  const [joinedLessons, setJoinedLessons] = useState([]);
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

      const response = await serverFetch(
        `/users/profile-picture/${userData.username}`,
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
    let endpoint;
    if (role === 'STUDENT') {
      endpoint = `/studyGroup/createdBy/username/${userInfoForm.username}`;
    } else if (role === 'PROFESSOR') {
      endpoint = `/lesson/createdBy/username/${userInfoForm.username}`;
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

  async function getUserJoinedGroups(type) {
    const endpoint = `/${type}/active/${userInfoForm.username}`;
    const options = {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json'
      }
    };
    try {
      const response = await serverFetch(endpoint, options);
      if (response.ok) {
        const text = await response.text();
        if (text) {
          const data = JSON.parse(text);
          if (type === 'studyGroup') setJoinedGroups(data);
          else if (type === 'lesson') setJoinedLessons(data);
        } else {
          console.log('Empty response body');
        }
      } else {
        console.log('response error');
      }
    } catch (error) {
      console.log(error);
    }
  }

  const handleLeave = (id, type) => {
    if (type === 'group') {
      setJoinedGroups((prevGroups) =>
        prevGroups.filter((group) => group.studyGroupId !== id)
      );
    } else if (type === 'lesson') {
      setJoinedLessons((prevLessons) =>
        prevLessons.filter((lesson) => lesson.lessonId !== id)
      );
    }
  };

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
  }, [isProfileSetupComplete, navigate, username]);

  useEffect(() => {
    const fetchGroups = async () => {
      if (role) {
        await getUserGroups(role);
        await getUserJoinedGroups('studyGroup');
        await getUserJoinedGroups('lesson');
      }
    };
    fetchGroups();
  }, [role]);

  useEffect(() => {
    const combined = [
      ...joinedGroups.map((group) => ({ ...group, type: 'group' })),
      ...joinedLessons.map((lesson) => ({ ...lesson, type: 'lesson' }))
    ];
    setUserJoinedGroups(combined);
  }, [joinedGroups, joinedLessons]);

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
              userGroups.map((item) =>
                role === 'STUDENT' ? (
                  <ActiveGroup
                    key={`group-${item.studyGroupId}`}
                    group={item}
                    joinedGroups={joinedGroups.map(
                      (group) => group.studyGroupId
                    )}
                    onLeave={handleLeave}
                  />
                ) : role === 'PROFESSOR' ? (
                  <ActiveLesson
                    key={`lesson-${item.lessonId}`}
                    lesson={item}
                    joinedGroups={joinedLessons.map(
                      (group) => group.studyGroupId
                    )}
                    onLeave={handleLeave}
                  />
                ) : null
              )
            )}
          </div>
        )}
        {!showActive && (
          <div className="userGroups">
            {userJoinedGroups.length === 0 ? (
              <p>Korisnik pridruzen niti jednoj grupi</p>
            ) : (
              <>
                {joinedGroups.map((item) => (
                  <ActiveGroup
                    key={`group-${item.studyGroupId}`}
                    group={item}
                    joinedGroups={joinedGroups.map(
                      (group) => group.studyGroupId
                    )}
                    onLeave={handleLeave}
                  />
                ))}
                {joinedLessons.map((item) => (
                  <ActiveLesson
                    key={`lesson-${item.lessonId}`}
                    lesson={item}
                    joinedGroups={joinedLessons.map(
                      (lesson) => lesson.lessonId
                    )}
                    onLeave={handleLeave}
                  />
                ))}
              </>
            )}
          </div>
        )}
      </div>
    </div>
  );
}

export default ProfileInfo;
