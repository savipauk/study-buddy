import { useState, useEffect } from 'react';
import '../styles/ProfilePage.css';
import Header from '../components/Header';
import { serverFetch } from '../hooks/serverUtils';
import { getHash } from '../hooks/serverUtils';
import PropTypes from 'prop-types';

function Profile() {
  return (
    <>
      <UserForm></UserForm>
    </>
  );
}

function UserForm() {
  //Route needs to be modified when it's implemented od backend
  async function getUserData(userId) {
    const endpoint = `/users/${userId}`;
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
        return data;
      } else {
        console.log('Failed to fetch data', response.statusText);
        return null;
      }
    } catch (error) {
      console.log(error);
      return null;
    }
  }

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

  const [newPasswordHash, setNewPasswordHash] = useState('');
  const [isChanged, setIsChanged] = useState(false);

  const handleEditClick = () => setShowEditWindow(true);
  const handleCloseWindow = () => {
    setShowEditWindow(false);
    setShowPasswordWindow(false);
  };
  const handleSaveChanges = (updatedInfo) => {
    setUserInfoForm(updatedInfo);
    setShowEditWindow(false);
  };
  const handlePasswordChange = (e) => {
    e.preventDefault();
    setShowPasswordWindow(true);
  };

  const handlePasswordSave = () => {
    setShowPasswordWindow(false);
  };

  const handleSaveData = async (e) => {
    e.preventDefault();
    setIsChanged(false);

    //Route needs to be modified when it's implemented od backend
    const data = {
      firstName: userInfoForm.FirstName,
      lastName: userInfoForm.LastName,
      email: userInfoForm.Email,
      description: userInfoForm.Bio,
      hashedPassword: newPasswordHash
    };
    const endpoint = '/users/update';
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
        console.log(data);
      }
    } catch (error) {
      console.error(error);
    }
  };

  //Getting userId needs to be modified
  useEffect(() => {
    const fetchUserData = async () => {
      const userId = '1'; //OR: fetch by access token?
      const userData = await getUserData(userId);
      if (userData) {
        setUserHash(userData.hash);
        setUserInfoForm({
          FirstName: userData.firstName,
          LastName: userData.lastName,
          Username: userData.username,
          Email: userData.email,
          Bio: userData.bio
        });
      }
    };

    fetchUserData();
  }, []);

  function onChange(event) {
    const { name, value } = event.target;
    setUserInfoForm((oldForm) => ({ ...oldForm, [name]: value }));
    setIsChanged(true);
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
                <p className="aboutText">About</p>
                <textarea
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
                <h1 className="personalInfo">Personal Information</h1>
                <hr />
                <p className="categoryText">First Name:</p>
                <input
                  className="input"
                  name="FirstName"
                  onChange={onChange}
                  value={userInfoForm.FirstName}
                  readOnly={true}
                ></input>
                <p className="categoryText">Last Name:</p>
                <input
                  className="input"
                  name="LastName"
                  onChange={onChange}
                  value={userInfoForm.LastName}
                  readOnly={true}
                ></input>
                <p className="categoryText">Username:</p>
                <input
                  className="input"
                  name="Username"
                  onChange={onChange}
                  value={userInfoForm.Username}
                  readOnly={true}
                ></input>
                <p className="categoryText">Email:</p>
                <input
                  className="input"
                  name="Email"
                  onChange={onChange}
                  value={userInfoForm.Email}
                  readOnly={true}
                ></input>
                <button
                  className="changePassword"
                  onClick={handlePasswordChange}
                >
                  Change password
                </button>
              </div>
            </form>
          </div>
        </div>
        <div className="editWrapper">
          <button className="editButton" onClick={handleEditClick}>
            Edit!
          </button>
        </div>
        {isChanged && (
          <div className="editWrapper">
            <button className="editButton" onClick={handleSaveData}>
              Save!
            </button>
          </div>
        )}
        {showEditWindow && (
          <EditWindow
            userInfo={userInfoForm}
            onSave={handleSaveChanges}
            onClose={handleCloseWindow}
            hasChanged={setIsChanged}
          />
        )}
        {showPasswordWindow && (
          <PasswordChange
            onSave={handlePasswordSave}
            onClose={handleCloseWindow}
            hash={userHash}
            setNewPasswordHash={setNewPasswordHash}
            hasChanged={setIsChanged}
          />
        )}
      </div>
    </>
  );
}

function EditWindow({ userInfo, onSave, onClose, hasChanged }) {
  const [formData, setFormData] = useState(userInfo);
  const [validationMessage, setValidationMessage] = useState('');

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({ ...prevData, [name]: value }));
  };

  function isValid() {
    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    if (!formData.FirstName) {
      setValidationMessage('First name is required');
      return false;
    }
    if (!formData.LastName) {
      setValidationMessage('Last name is required');
      return false;
    }
    if (!formData.Username) {
      setValidationMessage('Username is required');
      return false;
    }
    if (!emailRegex.test(formData.Email)) {
      setValidationMessage('Email is invalid!');
      return false;
    }
    setValidationMessage('');
    return true;
  }

  const handleSaveClick = () => {
    if (!isValid()) {
      return;
    }
    hasChanged(true);
    onSave(formData);
  };

  return (
    <div className="modal">
      <div className="modalContent">
        <h1 className="editProfileText">Edit Profile</h1>
        <p>First Name:</p>
        <input
          className="inputEdit"
          name="FirstName"
          value={formData.FirstName}
          onChange={handleChange}
        />
        <p>Last Name:</p>
        <input
          className="inputEdit"
          name="LastName"
          value={formData.LastName}
          onChange={handleChange}
        />
        <p>Username:</p>
        <input
          className="inputEdit"
          name="Username"
          value={formData.Username}
          onChange={handleChange}
        />
        <p>Email:</p>
        <input
          className="inputEdit"
          name="Email"
          value={formData.Email}
          onChange={handleChange}
        />
        <p className="errorMessage">{validationMessage}</p>
        <button className="EditWindowButton" onClick={handleSaveClick}>
          Accept
        </button>
        <button className="EditWindowButton" onClick={onClose}>
          Cancel
        </button>
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
  hasChanged: PropTypes.func.isRequired
};

function PasswordChange({
  onSave,
  onClose,
  hash,
  setNewPasswordHash,
  hasChanged
}) {
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

  async function hashPassword() {
    let hashed = await getHash(formData.currentPassword);
    return hashed;
  }

  function isValid(oldHash, newHash) {
    if (formData.newPassword !== formData.confirmNewPassword) {
      setValidationMessage('Passwords do not match');
      return false;
    }

    //Until fetch works tempPass will bypass validation, needs to be removed later on!
    if (oldHash !== newHash && !(formData.currentPassword == 'tempPass')) {
      setValidationMessage('Password is incorect!');
      return false;
    }
    if (formData.newPassword.length < 8) {
      setValidationMessage('Password must be at least 8 characters long');
      return false;
    } else {
      setValidationMessage('');
      return true;
    }
  }

  const updatePaswword = async (e) => {
    e.preventDefault();
    const currentHash = await hashPassword(formData.currentPassword);
    if (!isValid(hash, currentHash)) {
      return;
    } else {
      const hashedPassword = await getHash(formData.newPassword);
      setNewPasswordHash(hashedPassword);
      hasChanged(true);
      onSave();
    }
  };

  return (
    <div className="modal">
      <div className="modalContent">
        <h1 className="editProfileText">Change Password</h1>
        <p>Current password</p>
        <input
          className="inputEdit"
          type="password"
          name="currentPassword"
          value={formData.currentPassword}
          onChange={handleChange}
        ></input>
        <p>New password</p>
        <input
          className="inputEdit"
          type="password"
          name="newPassword"
          value={formData.newPassword}
          onChange={handleChange}
        ></input>
        <p>Confirm new password</p>
        <input
          className="inputEdit"
          type="password"
          name="confirmNewPassword"
          value={formData.confirmNewPassword}
          onChange={handleChange}
        ></input>
        <p className="errorMessage">{validationMessage}</p>
        <button className="EditWindowButton" onClick={updatePaswword}>
          Accept
        </button>
        <button className="EditWindowButton" onClick={onClose}>
          Cancel
        </button>
      </div>
    </div>
  );
}
PasswordChange.propTypes = {
  onSave: PropTypes.func.isRequired,
  onClose: PropTypes.func.isRequired,
  hash: PropTypes.string.isRequired,
  setNewPasswordHash: PropTypes.func.isRequired,
  hasChanged: PropTypes.func.isRequired
};

export default Profile;
