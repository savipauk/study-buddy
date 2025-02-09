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
import { useNavigate } from 'react-router-dom';
import useAuth from '../hooks/useAuth';

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
  const [profilePictureUrl, setProfilePictureUrl] = useState('');
  const { signOut } = useAuth();
  const isProfileSetupComplete = JSON.parse(
    localStorage.getItem('isProfileSetupComplete')
  );

  const loggedInWithOAUTH =
    localStorage.getItem('is_logged_in_with_google') === 'true' || false;

  const handleEditClick = () => setShowEditWindow(true);
  const handleCloseWindow = () => {
    setShowEditWindow(false);
    setShowPasswordWindow(false);
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

  useEffect(() => {
    const fetchUserData = async () => {
      const userEmail = localStorage.getItem('user_email');
      if (!userEmail) {
        navigate('/');
        return;
      }

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

        const endpoint = `/users/profile-picture/${userData.username}`;
        const options = {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json'
          }
        };

        try {
          const response = await serverFetch(endpoint, options);
          if (response.ok) {
            const blob = await response.blob();
            if (blob.size > 0) {
              setProfilePictureUrl(URL.createObjectURL(blob));
            } else {
              setProfilePictureUrl(
                'https://static.vecteezy.com/system/resources/previews/005/129/844/non_2x/profile-user-icon-isolated-on-white-background-eps10-free-vector.jpg'
              );
            }
          }
        } catch (error) {
          console.error('Greška pri dohvaćanju profilne slike:', error);
        }
      }
    };

    if (isProfileSetupComplete) {
      fetchUserData();
    } else {
      navigate('/');
    }
  }, [isProfileSetupComplete, navigate]);

  const handleImageUpload = async (event) => {
    const file = event.target.files[0];
    if (file) {
      const formData = new FormData();
      formData.append('file', file);

      const endpoint = `/users/profile-picture/${userInfoForm.Username}`;
      const options = {
        method: 'POST',
        body: formData
      };
      const response = await serverFetch(endpoint, options);

      if (response.ok) {
        console.log('Profilna slika uspješno dodana.');
        const updatedResponse = await serverFetch(
          `/users/profile-picture/${userInfoForm.Username}`,
          {
            method: 'GET',
            headers: {
              'Content-Type': 'application/json'
            }
          }
        );
        if (updatedResponse.ok) {
          const blob = await updatedResponse.blob();
          setProfilePictureUrl(URL.createObjectURL(blob));
        }
      } else {
        console.error('Profilna slika nije uspješno dodana', response.status);
      }
    }
  };

  const handleDeactivationProfile = async () => {
    const userEmail = localStorage.getItem('user_email');
    if (!userEmail) {
      console.error('User email not found in local storage.');
      return;
    }

    const confirmDeactivation = window.confirm(
      'Jeste li sigurni da želite deaktivirati profil? Ako izvršite ovu radnju biti ćete izbačeni iz svih StudyGroup-a i Instrukcija u koje ste prijavljeni.'
    );

    if (!confirmDeactivation) return;

    const endpoint = `/users/profile/deactivate/${userEmail}`;
    const options = {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' }
    };

    try {
      const response = await serverFetch(endpoint, options);
      if (response.ok) {
        console.log('Profil je uspješno deaktiviran.');
        signOut();
        navigate('/');
      } else {
        console.error('Greška prilikom deaktivacije.');
      }
    } catch (error) {
      console.error('Greška:', error);
    }
  };

  const handleDeletingProfile = async () => {
    const userEmail = localStorage.getItem('user_email');
    if (!userEmail) {
      console.error('User email not found in local storage.');
      return;
    }

    const confirmDelete = window.confirm(
      'Jeste li sigurni da želite obrisati profil? Ova radnja ne može se poništiti.'
    );

    if (!confirmDelete) return;

    const endpoint = `/users/delete/${userEmail}`;
    const options = {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json'
      }
    };

    try {
      const response = await serverFetch(endpoint, options);
      if (response.ok) {
        console.log('Profil uspješno izbrisan.');
        signOut();
        navigate('/');
      } else {
        console.error('Failed to delete the profile.', response.status);
      }
    } catch (error) {
      console.error('Error occurred while deleting the profile:', error);
    }
  };

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
              <img src={profilePictureUrl} className="image" />
              <div className="wrapper">
                <input
                  type="file"
                  accept="image/*"
                  id="fileUpload"
                  style={{ display: 'none' }}
                  onChange={handleImageUpload}
                />
                <label htmlFor="fileUpload" className="uploadButton">
                  <i className="fa-solid fa-cloud-arrow-up"></i>
                </label>
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
                {!loggedInWithOAUTH && (
                  <button
                    className="changePassword"
                    onClick={handlePasswordChange}
                  >
                    Promijeni lozinku
                  </button>
                )}
              </div>
            </form>
          </div>
        </div>
        <div className="editWrapper">
          <button className="editButton" onClick={handleEditClick}>
            Uredite profil!
          </button>
          <button
            className="deactivateButton"
            onClick={handleDeactivationProfile}
          >
            Deaktivirajte profil!
          </button>
          <button className="deletingButton" onClick={handleDeletingProfile}>
            Obrišite profil!
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
      </div>
    </>
  );
}

function EditWindow({ userInfo, onSave, onClose, oauth }) {
  const [formData, setFormData] = useState(userInfo);
  const [validationMessage, setValidationMessage] = useState('');

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({ ...prevData, [name]: value }));
  };

  const handleSaveData = async () => {
    const data = {
      firstName: formData.FirstName,
      lastName: formData.LastName,
      email: formData.Email,
      description: formData.Bio,
      username: formData.Username
    };

    const userEmail = localStorage.getItem('user_email');
    const endpoint = `/users/profile/update/${userEmail}`;
    const options = {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(data)
    };

    try {
      const response = await serverFetch(endpoint, options);
      if (response.ok) {
        const data = await response.json();
        return data.message;
      }
    } catch (error) {
      console.error(error);
    }
  };

  function isValid() {
    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    if (!formData.FirstName) {
      setValidationMessage('Ime je obavezno');
      return false;
    }
    if (!formData.LastName) {
      setValidationMessage('Prezime je obavezno');
      return false;
    }
    if (!formData.Username) {
      setValidationMessage('Korisničko ime je obavezno');
      return false;
    }
    if (!emailRegex.test(formData.Email) && !oauth) {
      setValidationMessage('Email nije ispravan!');
      return false;
    }
    setValidationMessage('');
    return true;
  }

  const handleSaveClick = async (e) => {
    e.preventDefault();
    if (!isValid()) {
      return;
    } else {
      const message = await handleSaveData();
      if (message == 'USERNAME_EXISTS') {
        setValidationMessage('Korisničko ime se već koristi');
        return;
      }
      if (message == 'EMAIL_EXISTS') {
        setValidationMessage('Email se već koristi');
        return;
      }
      if (message == 'UPDATE_OK') {
        onSave(formData);
      }
    }
  };

  return (
    <div className="modal">
      <div className="modalContent">
        <h1 className="editProfileText">Uredi profil</h1>
        <p>Ime</p>
        <input
          className="inputEdit"
          name="FirstName"
          value={formData.FirstName}
          onChange={handleChange}
        />
        <p>Prezime</p>
        <input
          className="inputEdit"
          name="LastName"
          value={formData.LastName}
          onChange={handleChange}
        />
        <p>Korisničko ime</p>
        <input
          className="inputEdit"
          name="Username"
          value={formData.Username}
          onChange={handleChange}
        />
        {!oauth && (
          <>
            <p>Email:</p>
            <input
              className="inputEdit"
              name="Email"
              value={formData.Email}
              onChange={handleChange}
            />
          </>
        )}
        <p className="aboutText">Opis</p>
        <textarea
          className="aboutInput"
          name="Bio"
          onChange={handleChange}
          value={formData.Bio}
        ></textarea>

        <p className="errorMessage">{validationMessage}</p>
        <div className="EditProfileButtonDiv">
          <button className="EditWindowButton" onClick={handleSaveClick}>
            Primijeni
          </button>
          <button className="EditWindowButton" onClick={onClose}>
            Odbaci
          </button>
        </div>
      </div>
    </div>
  );
}

EditWindow.propTypes = {
  userInfo: PropTypes.shape({
    FirstName: PropTypes.string,
    LastName: PropTypes.string,
    Username: PropTypes.string,
    Email: PropTypes.string,
    Bio: PropTypes.string
  }).isRequired,
  onSave: PropTypes.func.isRequired,
  onClose: PropTypes.func.isRequired,
  oauth: PropTypes.bool.isRequired
};

function PasswordChange({ onSave, onClose, hash }) {
  const [validationMessage, setValidationMessage] = useState('');
  const [formData, setFormData] = useState({
    currentPassword: '',
    newPassword: '',
    confirmNewPassword: ''
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({ ...prevData, [name]: value }));
  };

  async function isValid(enteredPassword, hash) {
    if (formData.newPassword !== formData.confirmNewPassword) {
      setValidationMessage('Lozinke se ne podudaraju');
      return false;
    }
    if (formData.newPassword.length < 8) {
      setValidationMessage('Lozinka mora imati najmanje 8 znakova');
      return false;
    }
    const match = await checkHash(enteredPassword, hash);
    if (!match) {
      setValidationMessage('Lozinka je netočna!');
      return false;
    } else {
      setValidationMessage('');
      return true;
    }
  }

  const updatePaswword = async (e) => {
    e.preventDefault();
    const enteredPassword = formData.currentPassword;
    if (!(await isValid(enteredPassword, hash))) {
      return;
    } else {
      const currentHash = await getHash(formData.newPassword);
      await handleSaveNewPassword(currentHash);
      onSave(currentHash);
    }
  };

  const handleSaveNewPassword = async (newHash) => {
    const userEmail = localStorage.getItem('user_email');

    const data = {
      password: newHash,
      email: userEmail
    };

    const endpoint = `/users/profile/update/${userEmail}`;
    const options = {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(data)
    };

    try {
      await serverFetch(endpoint, options);
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <div className="modal">
      <div className="modalContent">
        <h1 className="editProfileText">Promjena lozinke</h1>
        <p>Trenutna lozinka</p>
        <input
          className="inputEdit"
          type="password"
          name="currentPassword"
          value={formData.currentPassword}
          onChange={handleChange}
        ></input>
        <p>Nova lozinka</p>
        <input
          className="inputEdit"
          type="password"
          name="newPassword"
          value={formData.newPassword}
          onChange={handleChange}
        ></input>
        <p>Potvrda nove lozinke</p>
        <input
          className="inputEdit"
          type="password"
          name="confirmNewPassword"
          value={formData.confirmNewPassword}
          onChange={handleChange}
        ></input>
        <p className="errorMessage">{validationMessage}</p>
        <button className="EditWindowButton" onClick={updatePaswword}>
          Primijeni
        </button>
        <button className="EditWindowButton" onClick={onClose}>
          Odbaci
        </button>
      </div>
    </div>
  );
}

PasswordChange.propTypes = {
  onSave: PropTypes.func.isRequired,
  onClose: PropTypes.func.isRequired,
  hash: PropTypes.string.isRequired
};

export default Profile;
