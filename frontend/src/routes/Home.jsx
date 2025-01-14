import '../styles/HomePage.css';
import '../styles/Login.css';
import Header from '../components/Header';
import { useState, useEffect } from 'react';
import { serverFetch } from '../hooks/serverUtils';
import CreateStudyGroupForm from '../components/StudyGroupForm';
import Lessons from '../components/Lessons';
import ActiveGroup from '../components/ActiveGroup';
import ActiveLesson from '../components/AcitveLesson';
import { useNavigate } from 'react-router-dom';
import PropTypes from 'prop-types';

function HomePage() {
  const [createClicked, setCreateClicked] = useState(false);
  const [groups, setGroups] = useState([]);
  const [lessons, setLessons] = useState([]);
  const role = localStorage.getItem('role');

  const [hasRefreshed, setHasRefreshed] = useState(false);
  const [isProfileSetupComplete, setIsProfileSetupComplete] = useState(() =>
    JSON.parse(localStorage.getItem('isProfileSetupComplete'))
  );

  const navigate = useNavigate();

  const handleCreateGroup = () => {
    setCreateClicked(true);
  };
  const handleCloseCreateGroup = () => {
    setCreateClicked(false);
  };

  const handleProfileSetupComplete = () => {
    localStorage.setItem('isProfileSetupComplete', true);
    setIsProfileSetupComplete(true); // Trigger re-render
  };

  useEffect(() => {
    if (role === 'ADMIN') {
      navigate('admin');
    }
  }, []);

  useEffect(() => {
    const fetchData = async () => {
      try {
        await getActiveGroups('studyGroup');
        await getActiveGroups('lesson');
      } catch (error) {
        console.error('Error fetching groups:', error);
      }
    };
    fetchData();
  }, [hasRefreshed]);

  useEffect(() => {
    if (createClicked || !isProfileSetupComplete) {
      document.body.classList.add('no-scroll');
    } else {
      document.body.classList.remove('no-scroll');
    }
  }, [createClicked, isProfileSetupComplete]);

  async function getActiveGroups(type) {
    const endpoint = `/${type}/active`;
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
        if (type === 'lesson') {
          setLessons(data);
        } else if (type === 'studyGroup') {
          setGroups(data);
        }
      } else {
        console.log('response error');
      }
    } catch (error) {
      console.log(error);
    }
  }
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
            {role === 'STUDENT' ? 'Kreiraj StudyGroup' : 'Kreiraj Instrukcije'}
          </button>
        </div>

        <h1 className="someText">AKTIVNO</h1>
      </div>
      {!isProfileSetupComplete && (
        <ProfileSetup finishSetup={handleProfileSetupComplete} />
      )}
      {createClicked && role === 'STUDENT' && (
        <CreateStudyGroupForm
          onClose={handleCloseCreateGroup}
          onCreateClick={() => setHasRefreshed(!hasRefreshed)}
        />
      )}
      {createClicked && role === 'PROFESSOR' && (
        <Lessons
          onClose={handleCloseCreateGroup}
          onCreateClick={() => setHasRefreshed(!hasRefreshed)}
        />
      )}
      <div className="activeLessons">
        {groups.length === 0 ? (
          <p>Treutno nema aktivnih studyGrupa</p>
        ) : (
          groups.map((group, index) => (
            <ActiveGroup key={index} group={group} />
          ))
        )}
        {lessons.length === 0 ? (
          <p>Trenutno nema aktivnih instrukcija</p>
        ) : (
          lessons.map((lesson, index) => (
            <ActiveLesson key={index} lesson={lesson} />
          ))
        )}
      </div>
    </>
  );
}

export default HomePage;

function ProfileSetup({ finishSetup }) {
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
        if (data.message === 'USERNAME_EXISTS') {
          setErrorMessage('Korisničko ime već postoji');
        } else {
          finishSetup();
          localStorage.setItem('role', data.studyRole);
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
      setErrorMessage('Ime je obavezno');
      return false;
    }
    if (!setupForm.lastName) {
      setErrorMessage('Prezime je obavezno');
      return false;
    }
    if (!setupForm.username) {
      setErrorMessage('Korisničko ime je obavezno');
      return false;
    }
    if (!setupForm.role) {
      setErrorMessage('Uloga je obavezna');
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
          <h1 className="helloText">Dovršite profil!</h1>
          <div className="inputDiv">
            <div className="nameWrapper">
              <input
                className="nameInfoInput"
                placeholder="Ime"
                type="text"
                name="firstName"
                value={setupForm.firstName}
                onChange={onChange}
              ></input>
              <input
                className="nameInfoInput"
                placeholder="Prezime"
                type="text"
                name="lastName"
                value={setupForm.lastName}
                onChange={onChange}
              ></input>
            </div>
            <input
              className="infoInput"
              type="text"
              placeholder="Korisničko ime"
              onChange={onChange}
              value={setupForm.username}
              name="username"
            ></input>
          </div>
          <input
            className="infoInput"
            type="text"
            placeholder="Lokacija"
            onChange={onChange}
            value={setupForm.location}
            name="location"
          ></input>
          <div className="dateOfBirth">
            <label className="dobTitle">Datum rođenja</label>
            <div className="dobSelector">
              <div className="dropdown">
                <select
                  name="day"
                  className="dobSelect"
                  value={day}
                  onChange={(e) => handleDOBChange('day', e.target.value)}
                >
                  <option value="">Dan</option>
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
                  <option value="">Mjesec</option>
                  {[
                    'Siječanj',
                    'Veljača',
                    'Ožujak',
                    'Travanj',
                    'Svibanj',
                    'Lipanj',
                    'Srpanj',
                    'Kolovoz',
                    'Rujan',
                    'Listopad',
                    'Studeni',
                    'Prosinac'
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
              Muško
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
              Žensko
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
              Profesor
            </label>
          </div>
          <p className="errorMessage">{errorMessage}</p>
          <div className="buttonDiv">
            <button className="inputButton" type="submit">
              Predaj
            </button>
          </div>
        </div>
      </form>
    </div>
  );
}

ProfileSetup.propTypes = {
  finishSetup: PropTypes.func.isRequired
};
