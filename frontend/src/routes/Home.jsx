import '../styles/HomePage.css';
import '../styles/Login.css';
import Header from '../components/Header';
import useAuth from '../hooks/useAuth';
import { useState } from 'react';
import { serverFetch } from '../hooks/serverUtils';

function HomePage() {
  const { isProfileSetupComplete } = useAuth();
  return (
    <div>
      <div className={isProfileSetupComplete ? 'homePageWrapper' : 'blurred'}>
        <Header></Header>
        <h1 className="someText">HOMEPAGE</h1>
      </div>
      {!isProfileSetupComplete && <ProfileSetup />}
    </div>
  );
}

export default HomePage;

function ProfileSetup() {
  const { setIsProfileSetupComplete } = useAuth();
  const [errorMessage, setErrorMessage] = useState('');
  const [setupForm, setSetupForm] = useState({
    username: '',
    firstName: '',
    lastName: '',
    role: ''
  });

  function onChange(event) {
    const { name, value } = event.target;
    setSetupForm((oldForm) => ({ ...oldForm, [name]: value }));
  }

  async function setupFinish() {
    //Remove when backend is updated
    setIsProfileSetupComplete(true);
    const userEmail = localStorage.getItem('user_email');
    const data = {
      username: setupForm.username,
      firstName: setupForm.firstName,
      lastName: setupForm.lastName,
      role: setupForm.role.toUpperCase()
    };
    const endpoint = `/users/profile/setup/${userEmail}`;
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
        if (data.message === 'USERNAME_TAKEN') {
          setErrorMessage('Username postoji');
        } else {
          setIsProfileSetupComplete(true);
        }
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

  function isValid() {
    if (!setupForm.firstName) {
      setErrorMessage('First name is required');
      return false;
    }
    if (!setupForm.lastName) {
      setErrorMessage('Last name is required');
      return false;
    }
    if (!setupForm.username) {
      setErrorMessage('Username is required');
      return false;
    }
    if (!setupForm.role) {
      setErrorMessage('Role is required');
      return false;
    }
    setErrorMessage('');
    return true;
  }
  function onSubmit(e) {
    e.preventDefault();
    if (isValid()) {
      setupFinish();
    }
    return;
  }

  return (
    <div className="formWrapper">
      <form className="forms" onSubmit={onSubmit}>
        <div className="formDiv">
          <h1 className="helloText">Profile setup!</h1>
          <div className="inputDiv">
            <div className="nameWrapper">
              <input
                className="nameInfoInput"
                placeholder="First Name"
                type="text"
                name="firstName"
                value={setSetupForm.firstName}
                onChange={onChange}
              ></input>
              <input
                className="nameInfoInput"
                placeholder="Last Name"
                type="text"
                name="lastName"
                value={setSetupForm.lastName}
                onChange={onChange}
              ></input>
            </div>
            <input
              className="infoInput"
              type="text"
              placeholder="Username"
              onChange={onChange}
              value={setSetupForm.username}
              name="username"
            ></input>
          </div>
          <div className="roleSelection">
            <input
              className="roleRadioButton"
              type="radio"
              name="role"
              value={'Student'}
              id="roleStudent"
              checked={setupForm.role === 'Student'}
              onChange={onChange}
            ></input>
            <label htmlFor="roleStudent" className="toggleOption">
              Student
            </label>
            <input
              className="roleRadioButton"
              type="radio"
              name="role"
              value={'Professor'}
              id="roleProfessor"
              checked={setupForm.role === 'Professor'}
              onChange={onChange}
            ></input>
            <label htmlFor="roleProfessor" className="toggleOption">
              Professor
            </label>
          </div>
          <p className="errorMessage">{errorMessage}</p>
          <div className="buttonDiv">
            <button className="inputButton" type="submit">
              Finish
            </button>
          </div>
        </div>
      </form>
    </div>
  );
}
