import '../styles/HomePage.css';
import '../styles/Login.css';
import Header from '../components/Header';
import useAuth from '../hooks/useAuth';
import { useState, useEffect } from 'react';
import { serverFetch } from '../hooks/serverUtils';
import CreateStudyGroupForm from '../components/StudyGroupForm';
import Lessons from '../components/Lessons';

function HomePage() {
  const { isProfileSetupComplete, role } = useAuth();
  const [createClicked, setCreateClicked] = useState(false);

  const handleCreateGroup = () => {
    setCreateClicked(true);
  };
  const handleCloseCreateGroup = () => {
    setCreateClicked(false);
  };
  useEffect(() => {
    if (createClicked || !isProfileSetupComplete) {
      document.body.classList.add('no-scroll');
    } else {
      document.body.classList.remove('no-scroll');
    }
  }, [createClicked, isProfileSetupComplete]);
  return (
    <>
      <div
        className={`${
          createClicked || !isProfileSetupComplete
            ? 'blurred'
            : 'homePageWrapper'
        }`}
      >
        <Header></Header>
        <div className="newStudyGroup">
          <button className="newStudyGroupButton" onClick={handleCreateGroup}>
            Kreiraj StudyGroup
          </button>
        </div>

        <h1 className="someText">HOMEPAGE</h1>
      </div>
      {!isProfileSetupComplete && <ProfileSetup />}
      {createClicked && role === 'STUDENT' && (
        <CreateStudyGroupForm onClose={handleCloseCreateGroup} />
      )}
      {createClicked && role === 'PROFESSOR' && (
        <Lessons onClose={handleCloseCreateGroup} />
      )}
    </>
  );
}

export default HomePage;

function ProfileSetup() {
  const { setIsProfileSetupComplete, setRole } = useAuth();
  const [errorMessage, setErrorMessage] = useState('');
  const [setupForm, setSetupForm] = useState({
    username: '',
    firstName: '',
    lastName: '',
    role: '',
    gender: '',
    location: '',
    dob: ''
  });

  const [day, setDay] = useState('');
  const [month, setMonth] = useState('');
  const [year, setYear] = useState('');
  const handleDOBChange = (type, value) => {
    if (type === 'day') {
      setDay(value);
    }
    if (type === 'month') {
      setMonth(value);
    }
    if (type === 'year') {
      setYear(value);
    }

    const updatedDay = type === 'day' ? value : day;
    const updatedMonth = type === 'month' ? value : month;
    const updatedYear = type === 'year' ? value : year;
    if (updatedDay && updatedMonth && updatedYear) {
      const dob = `${updatedYear}-${updatedMonth.padStart(
        2,
        '0'
      )}-${updatedDay.padStart(2, '0')}`;
      setSetupForm((prevForm) => ({
        ...prevForm,
        dob: dob
      }));
    }
  };

  function onChange(event) {
    const { name, value } = event.target;
    setSetupForm((oldForm) => ({ ...oldForm, [name]: value }));
  }

  async function setupFinish() {
    const userEmail = localStorage.getItem('user_email');
    const data = {
      email: localStorage.getItem('user_email'),
      username: setupForm.username,
      firstName: setupForm.firstName,
      lastName: setupForm.lastName,
      role: setupForm.role.toUpperCase(),
      gender: setupForm.gender,
      city: setupForm.location,
      dateOfBirth: setupForm.dob
    };
    console.log(data);
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
        if (data.message === 'USERNAME_TAKEN') {
          setErrorMessage('Username postoji');
        } else {
          setIsProfileSetupComplete(true);
          setRole(setupForm.role);
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
    <div className="setupWrapper">
      <form className="forms" onSubmit={onSubmit}>
        <div className="formDiv">
          <h1 className="helloText">Dovrši profil!</h1>
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
          <input
            className="infoInput"
            type="text"
            placeholder="Location"
            onChange={onChange}
            value={setupForm.location}
            name="location"
          ></input>
          <div className="dateOfBirth">
            <label className="dobTitle">Date of Birth</label>
            <div className="dobSelector">
              <div className="dropdown">
                <select
                  name="day"
                  className="dobSelect"
                  value={day}
                  onChange={(e) => handleDOBChange('day', e.target.value)}
                >
                  <option value="">Day</option>
                  {Array.from({ length: 31 }, (_, i) => (
                    <option key={i + 1} value={i + 1}>
                      {i + 1}
                    </option>
                  ))}
                </select>
              </div>
              <div className="dropdown">
                <select
                  name="month"
                  className="dobSelect"
                  value={month}
                  onChange={(e) => handleDOBChange('month', e.target.value)}
                >
                  <option value="">Month</option>
                  {[
                    'January',
                    'February',
                    'March',
                    'April',
                    'May',
                    'June',
                    'July',
                    'August',
                    'September',
                    'October',
                    'November',
                    'December'
                  ].map((month, i) => (
                    <option key={i + 1} value={i + 1}>
                      {month}
                    </option>
                  ))}
                </select>
              </div>
              <div className="dropdown">
                <select
                  name="year"
                  className="dobSelect"
                  value={year}
                  onChange={(e) => handleDOBChange('year', e.target.value)}
                >
                  <option value="">Year</option>
                  {Array.from({ length: 100 }, (_, i) => {
                    const year = new Date().getFullYear() - i;
                    return (
                      <option key={year} value={year}>
                        {year}
                      </option>
                    );
                  })}
                </select>
              </div>
            </div>
          </div>
          <div className="genderSelection">
            <input
              className="genderRadioButton"
              type="radio"
              name="gender"
              value={'M'}
              id="genderMale"
              checked={setupForm.gender === 'M'}
              onChange={onChange}
            ></input>
            <label htmlFor="genderMale" className="toggleOption">
              Male
            </label>
            <input
              className="genderRadioButton"
              type="radio"
              name="gender"
              value={'F'}
              id="genderFemale"
              checked={setupForm.gender === 'F'}
              onChange={onChange}
            ></input>
            <label htmlFor="genderFemale" className="toggleOption">
              Female
            </label>
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
