import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { GoogleLogin } from '@react-oauth/google';
import '../styles/Login.css';
import useAuth from '../hooks/useAuth';
import { serverFetch } from '../hooks/serverUtils';
import { getHash } from '../hooks/serverUtils';

function RegisterForm() {
  const { signIn, signInWithGoogle } = useAuth();
  const navigate = useNavigate();

  const [registerForm, setRegisterForm] = useState({
    firstName: '',
    lastName: '',
    username: '',
    email: '',
    password: '',
    confirmPassword: '',
    role: '',
    gender: '',
    location: '',
    dob: ''
  });

  const [day, setDay] = useState('');
  const [month, setMonth] = useState('');
  const [year, setYear] = useState('');
  const [errorMessage, setErrorMessage] = useState('');

  function onChange(event) {
    const { name, value } = event.target;
    setRegisterForm((oldForm) => ({ ...oldForm, [name]: value }));
  }

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
      setRegisterForm((prevForm) => ({
        ...prevForm,
        dob: dob
      }));
    }
  };
  async function storeUserToDatabase(hash) {
    const data = {
      firstName: registerForm.firstName,
      lastName: registerForm.lastName,
      username: registerForm.username,
      email: registerForm.email,
      password: hash,
      role: registerForm.role.toUpperCase(),
      gender: registerForm.gender,
      city: registerForm.location,
      dateOfBirth: registerForm.dob
    };
    const endpoint = '/login/register';
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
        const message = data.registration;
        const role = data.studyRole;
        if (message === 'REGISTRATION_OK') {
          setErrorMessage('');
          signIn(registerForm.email, role);
          navigate('/users/home');
        }
        if (message === 'EMAIL_EXISTS') {
          setErrorMessage('Email se već koristi');
        }
        if (message === 'USERNAME_EXISTS') {
          setErrorMessage('Korisničko ime se već koristi');
        }
      }
    } catch (error) {
      console.error(error);
    }
  }

  async function loginWithGoogle(response) {
    const { credential } = response;

    const endpoint = '/login/oauth';
    const data = {
      credential: credential
    };
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
        const registration = data.registration;
        const email = data.email;
        const role = data.studyRole;
        if (registration === 'REGISTRATION_OAUTH_OK') {
          localStorage.setItem('isProfileSetupComplete', false);
          signInWithGoogle(credential, email, '');
        } else if (registration === 'LOGIN_OAUTH_OK') {
          localStorage.setItem('isProfileSetupComplete', true);
          signInWithGoogle(credential, email, role);
        }
        navigate('/users/home');
      }
    } catch (error) {
      console.log(error);
    }
  }

  async function onSubmit(e) {
    e.preventDefault();

    if (!isValid()) {
      return;
    }

    try {
      const hash = await getHash(registerForm.password);
      await storeUserToDatabase(hash);
    } catch (err) {
      console.error('Error processing password:', err);
    }
  }

  function isValid() {
    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    if (registerForm.password !== registerForm.confirmPassword) {
      setErrorMessage('Lozinke se ne podudaraju!');
      return false;
    }
    if (!emailRegex.test(registerForm.email)) {
      setErrorMessage('Email nije ispravan!');
      return false;
    }
    if (registerForm.password.length < 8) {
      setErrorMessage('Lozinka mora imati najmanje 8 znakova');
      return false;
    }
    if (!registerForm.username) {
      setErrorMessage('Korisničko ime je obavezno');
      return false;
    }
    if (!registerForm.password) {
      setErrorMessage('Lozinka je obavezna');
      return false;
    }
    if (!registerForm.firstName) {
      setErrorMessage('Ime je obavezno');
      return false;
    }
    if (!registerForm.lastName) {
      setErrorMessage('Prezime je obavezno');
      return false;
    }
    if (!registerForm.role) {
      setErrorMessage('Uloga je obavezna');
      return false;
    }
    setErrorMessage('');
    return true;
  }

  return (
    <div className='formWrapper'>
      <form className='forms' onSubmit={onSubmit}>
        <div className='formDiv'>
          <h1 className='helloText'>Pozdrav!</h1>
          <h2 className='createNewText'>Kreirajte novi račun</h2>
          <div className='inputDiv'>
            <div className='nameWrapper'>
              <input
                className='nameInfoInput'
                placeholder='Ime'
                type='text'
                name='firstName'
                value={registerForm.firstName}
                onChange={onChange}
              ></input>
              <input
                className='nameInfoInput'
                placeholder='Prezime'
                type='text'
                name='lastName'
                value={registerForm.lastName}
                onChange={onChange}
              ></input>
            </div>
            <input
              className='infoInput'
              type='text'
              placeholder='Korisničko ime'
              onChange={onChange}
              value={registerForm.username}
              name='username'
            ></input>
            <input
              className='infoInput'
              type='text'
              placeholder='Email'
              onChange={onChange}
              value={registerForm.email}
              name='email'
            />
          </div>
          <div className='passwordDiv'>
            <input
              className='passwordInput'
              type='password'
              placeholder='Lozinka'
              onChange={onChange}
              value={registerForm.password}
              name='password'
            />
            <input
              className='passwordInput'
              type='password'
              placeholder='Potvrdi lozinku'
              onChange={onChange}
              value={registerForm.confirmPassword}
              name='confirmPassword'
            />
          </div>
          <input
            className='infoInput'
            type='text'
            placeholder='Lokacija'
            onChange={onChange}
            value={registerForm.location}
            name='location'
          ></input>
          <div className='dateOfBirth'>
            <label className='dobTitle'>Datum rođenja</label>
            <div className='dobSelector'>
              <div className='dropdown'>
                <select
                  name='day'
                  className='dobSelect'
                  value={day}
                  onChange={(e) => handleDOBChange('day', e.target.value)}
                >
                  <option value=''>Dan</option>
                  {Array.from({ length: 31 }, (_, i) => (
                    <option key={i + 1} value={i + 1}>
                      {i + 1}
                    </option>
                  ))}
                </select>
              </div>
              <div className='dropdown'>
                <select
                  name='month'
                  className='dobSelect'
                  value={month}
                  onChange={(e) => handleDOBChange('month', e.target.value)}
                >
                  <option value=''>Mjesec</option>
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
              <div className='dropdown'>
                <select
                  name='year'
                  className='dobSelect'
                  value={year}
                  onChange={(e) => handleDOBChange('year', e.target.value)}
                >
                  <option value=''>Godina</option>
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
          <div className='genderSelection'>
            <input
              className='genderRadioButton'
              type='radio'
              name='gender'
              value={'M'}
              id='genderMale'
              checked={registerForm.gender === 'M'}
              onChange={onChange}
            ></input>
            <label htmlFor='genderMale' className='toggleOption'>
              Muško
            </label>
            <input
              className='genderRadioButton'
              type='radio'
              name='gender'
              value={'F'}
              id='genderFemale'
              checked={registerForm.gender === 'F'}
              onChange={onChange}
            ></input>
            <label htmlFor='genderFemale' className='toggleOption'>
              Žensko
            </label>
          </div>
          <div className='roleSelection'>
            <input
              className='roleRadioButton'
              type='radio'
              name='role'
              value={'Student'}
              id='roleStudent'
              checked={registerForm.role === 'Student'}
              onChange={onChange}
            ></input>
            <label htmlFor='roleStudent' className='toggleOption'>
              Student
            </label>
            <input
              className='roleRadioButton'
              type='radio'
              name='role'
              value={'Professor'}
              id='roleProfessor'
              checked={registerForm.role === 'Professor'}
              onChange={onChange}
            ></input>
            <label htmlFor='roleProfessor' className='toggleOption'>
              Profesor
            </label>
          </div>
          <p className='errorMessage'>{errorMessage}</p>
          <div className='buttonDiv'>
            <button className='inputButton' type='submit'>
              Kreirajte račun!
            </button>
          </div>
          <div className='oauth'>
            <p className='signUpText'> Ili se prijavite sa... </p>
            <GoogleLogin
              onSuccess={loginWithGoogle}
              onError={() => {
                console.log('Login Failed');
              }}
            />
          </div>
        </div>
        <div className='redirect'>
          <p className='account'>Već imate račun?</p>
          <a className='link' href='/users/login'>
            Prijavite se
          </a>
        </div>
      </form>
    </div>
  );
}

export default RegisterForm;
