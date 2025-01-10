import { useState, useEffect } from 'react';
import '../styles/ProfilePage.css';
import Header from '../components/Header';
import {
  serverFetch,
  getUserData,
  getHash,
  checkHash
} from '../hooks/serverUtils';
import PropTypes from 'prop-types';
import useAuth from '../hooks/useAuth';
import { useNavigate } from 'react-router-dom';
import ReportForm from '../components/ReportForm'; // Import sa nazivom ReportForm

function Profile() {
  return (
      <>
        <UserForm></UserForm>
      </>
  );
}

function UserForm() {
  const navigate = useNavigate();
  const [userInfoForm, setUserInfoForm] = useState({
    FirstName: '',
    LastName: '',
    Username: '',
    Email: '',
    Bio: ''
  });

  const [userHash, setUserHash] = useState('');
  const [showEditWindow, setShowEditWindow] = useState(false);
  const [showPasswordWindow, setShowPasswordWindow] = useState(false);
  const [showReportWindow, setShowReportWindow] = useState(false); // Novo stanje za prikaz forme za prijavu

  const { isProfileSetupComplete } = useAuth();

  const loggedInWithOAUTH =
      localStorage.getItem('is_logged_in_with_google') === 'true' || false;

  const handleEditClick = () => setShowEditWindow(true);
  const handleCloseWindow = () => {
    setShowEditWindow(false);
    setShowPasswordWindow(false);
    setShowReportWindow(false); // Zatvara formu prijave
  };
  const handleSaveChanges = async (updatedInfo) => {
    setUserInfoForm(updatedInfo);
    setShowEditWindow(false);
  };
  const handlePasswordChange = (e) => {
    e.preventDefault();
    setShowPasswordWindow(true);
  };

  const handlePasswordSave = (hash) => {
    setUserHash(hash);
    setShowPasswordWindow(false);
  };

  const handleReportUser = () => setShowReportWindow(true);

  useEffect(() => {
    const fetchUserData = async () => {
      const userEmail = localStorage.getItem('user_email');
      const userData = await getUserData(userEmail);
      if (userData) {
        setUserHash(userData.password);
        setUserInfoForm({
          FirstName: userData.firstName,
          LastName: userData.lastName,
          Username: userData.username,
          Email: userData.email,
          Bio: userData.description
        });
      }
    };
    if (isProfileSetupComplete) {
      fetchUserData();
    } else {
      navigate('/users/home');
    }
  }, [isProfileSetupComplete, navigate]);

  function onChange(event) {
    const { name, value } = event.target;
    setUserInfoForm((oldForm) => ({ ...oldForm, [name]: value }));
  }

  return (
      <>
        <Header />
        <div className="profile">
          <div className="allInfoWrapper">
            <div className="profilePictureWrapper">
              <div className="profilePicture">
                <img
                    src="https://static.vecteezy.com/system/resources/previews/005/129/844/non_2x/profile-user-icon-isolated-on-white-background-eps10-free-vector.jpg"
                    className="image"
                ></img>
                <div className="wrapper">
                  <button className="uploadButton">
                    <i className="fa-solid fa-cloud-arrow-up"></i>
                  </button>
                  <p className="aboutText">Opis</p>
                  <textarea
                      readOnly
                      className="aboutInput"
                      name="Bio"
                      onChange={onChange}
                      value={userInfoForm.Bio}
                  ></textarea>
                </div>
              </div>
            </div>
            <div className="infoformWrapper">
              <form className="userInfoForm">
                <div className="personalInfoWrapper">
                  <h1 className="personalInfo">Pregled profila</h1>
                  <hr />
                  <p className="categoryText">Ime</p>
                  <input
                      className="input"
                      name="FirstName"
                      onChange={onChange}
                      value={userInfoForm.FirstName}
                      readOnly={true}
                  ></input>
                  <p className="categoryText">Prezime</p>
                  <input
                      className="input"
                      name="LastName"
                      onChange={onChange}
                      value={userInfoForm.LastName}
                      readOnly={true}
                  ></input>
                  <p className="categoryText">Korisničko ime</p>
                  <input
                      className="input"
                      name="Username"
                      onChange={onChange}
                      value={userInfoForm.Username}
                      readOnly={true}
                  ></input>
                  <p className="categoryText">Email</p>
                  <input
                      className="input"
                      name="Email"
                      onChange={onChange}
                      value={userInfoForm.Email}
                      readOnly={true}
                  ></input>
                </div>
              </form>
            </div>
          </div>
          <div className="editWrapper">
            <button className="editButton" onClick={handleEditClick}>
              Uredi profil!
            </button>
            <button className="reportButton" onClick={handleReportUser}>
              Prijavi korisnika!
            </button>
          </div>
          {showEditWindow && (
              <EditWindow
                  userInfo={userInfoForm}
                  onSave={handleSaveChanges}
                  onClose={handleCloseWindow}
                  oauth={loggedInWithOAUTH}
              />
          )}
          {showPasswordWindow && (
              <PasswordChange
                  onSave={handlePasswordSave}
                  onClose={handleCloseWindow}
                  hash={userHash}
              />
          )}
          {showReportWindow && (
              <ReportForm
                  onClose={handleCloseWindow}
                  userEmail={localStorage.getItem('user_email')}
                  reportedUserEmail={userInfoForm.Email}
              />
          )}
        </div>
      </>
  );
}

export default Profile;
