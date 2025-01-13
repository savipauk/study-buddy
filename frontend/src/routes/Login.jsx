import { useNavigate } from 'react-router-dom';
import { useState, useEffect } from 'react';
import { GoogleLogin } from '@react-oauth/google';
import '../styles/Login.css';
import useAuth from '../hooks/useAuth';
import { serverFetch } from '../hooks/serverUtils';

function LoginForm() {
  const [loginForm, setLoginForm] = useState({ username: '', password: '' });
  const navigate = useNavigate();
  const { signIn, signInWithGoogle } = useAuth();
  const [errorMessage, setErrorMessage] = useState('');

  useEffect(() => {
    if (localStorage.getItem('isSignedIn') === 'true') {
      navigate('/users/home');
    }
  });

  function onChange(event) {
    const { name, value } = event.target;
    setLoginForm((oldForm) => ({ ...oldForm, [name]: value }));
  }

  async function loginUser() {
    const data = {
      username: loginForm.username,
      password: loginForm.password
    };

    const endpoint = '/login/login';
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

        const message = data.passwordCheck;
        const email = data.email;
        const role = data.studyRole;
        if (message === 'DOESNT_EXIST') {
          setErrorMessage('Korisnik ne postoji');
        }
        if (message === 'NOT_OK') {
          setErrorMessage('Lozinka neispravna');
        }
        if (message === 'OK') {
          setErrorMessage('');
          signIn(email, role);
          if (role === 'ADMIN') {
            navigate('/admin');
          } else {
            console.log('krivi', role);
            navigate('/users/home');
          }
        }
      }
    } catch (error) {
      console.log(error);
    }
  }

  function onSubmit(e) {
    e.preventDefault();
    loginUser();
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
        } else if (registration === 'LOGIN_OAUTH_OK') {
          localStorage.setItem('isProfileSetupComplete', true);
        }
        signInWithGoogle(credential, email, role);
        navigate('/users/home');
      }
    } catch (error) {
      console.log(error);
    }
  }

  return (
    <>
      <div className="formWrapper">
        <form className="forms" onSubmit={onSubmit}>
          <div className="formDiv">
            <h1 className="helloText">Pozdrav!</h1>
            <div className="inputDiv">
              <input
                className="infoInput"
                placeholder="Korisničko ime"
                name="username"
                onChange={onChange}
                value={loginForm.username}
              />
              <input
                className="passwordInput"
                placeholder="Lozinka"
                name="password"
                onChange={onChange}
                type="password"
                value={loginForm.password}
              />
            </div>
            <p className="errorMessage">{errorMessage}</p>
            <div className="buttonDiv">
              <button className="inputButton" type="submit">
                Prijavi se
              </button>
            </div>
            <div className="redirect">
              <p className="account">Nemate račun?</p>
              <a href="/users/register" className="link">
                Registrirajte se
              </a>
            </div>
            <div className="oauth">
              <p className="signUpText"> Ili se prijavite sa... </p>
              <GoogleLogin
                onSuccess={loginWithGoogle}
                onError={() => {
                  console.log('Login Failed');
                }}
              />
            </div>
          </div>
        </form>
      </div>
    </>
  );
}
export default LoginForm;
