import { useEffect, useState } from 'react';
import { serverFetch } from '../hooks/serverUtils';
import ReportForm from './ReportForm';
import '../styles/StudentProfile.css';
import PropTypes from 'prop-types';

function StudentProfile({ onClose, username }) {
  const [userInfoForm, setUserInfoForm] = useState({
    FirstName: '',
    LastName: '',
    Bio: '',
    Email: ''
  });

  const [profilePictureUrl, setProfilePictureUrl] = useState('');

  const [showReportWindow, setShowReportWindow] = useState(false);

  const handleCloseWindow = () => {
    setShowReportWindow(false);
  };

  const handleReportUser = () => setShowReportWindow(true);

  const getStudentProfile = async (username) => {
    const endpoint = `/users/profileByUsername/${username}`;
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
        setUserInfoForm({
          FirstName: data.firstName || '-',
          LastName: data.lastName || '-',
          Bio: data.description || '-',
          Email: data.email || ''
        });
      } else {
        console.error('Failed to fetch user profile');
      }

      const imageResponse = await serverFetch(
        `/users/profile-picture/${username}`,
        {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json'
          }
        }
      );
      if (imageResponse.ok) {
        const blob = await imageResponse.blob();
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
    } catch (error) {
      console.error('Greška:', error);
    }
  };

  useEffect(() => {
    getStudentProfile(username);
  }, [username]);

  return (
    <div className="wholePage">
      <div className="profileContainer">
        <div className="profile-container">
          <div className="profile-header">
            <div className="profile-picture">
              <img src={profilePictureUrl} />
            </div>
            <div className="profile-info">
              <p>Ime: {userInfoForm.FirstName}</p>
              <p>Prezime: {userInfoForm.LastName}</p>
              <p>Opis: {userInfoForm.Bio}</p>
              <div className="showProfile">
                <button className="showProfileButton">Prikaži Profil</button>
              </div>
            </div>
          </div>
          <div className="buttons">
            <button className="report-button" onClick={handleReportUser}>
              Prijavite korisnika!
            </button>
            <button className="close-button" onClick={onClose}>
              Zatvori
            </button>
          </div>
        </div>
        {showReportWindow && (
          <ReportForm
            onClose={handleCloseWindow}
            userEmail={localStorage.getItem('user_email')}
            reportedUserEmail={userInfoForm.Email}
          />
        )}
      </div>
    </div>
  );
}

StudentProfile.propTypes = {
  onClose: PropTypes.func.isRequired,
  username: PropTypes.string.isRequired
};

export default StudentProfile;
