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
    email: '',
    password: '',
    confirmPassword: ''
  });
  const [errorMessage, setErrorMessage] = useState('');

  function onChange(event) {
    const { name, value } = event.target;
    setRegisterForm((oldForm) => ({ ...oldForm, [name]: value }));
  }

  async function storeUserToDatabase(hash) {
    const data = {
      email: JSON.stringify(registerForm.email),
      firstName: '',
      lastName: '',
      hashedPassword: hash,
      studyRole: 'STUDENT'
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
        console.log(data);
        signIn();
        navigate('/users/home');
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
        signInWithGoogle(credential);
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
      console.error('Error processing password:', err);
    }
  }

  function isValid() {
    if (registerForm.password !== registerForm.confirmPassword) {
      setErrorMessage('Passwords do not match!');
      setErrorMessage('Passwords do not match!');
      return false;
    }
    setErrorMessage('');
    setErrorMessage('');
    return true;
  }

  return (
    <div className="formWrapper">
      <form className="forms" onSubmit={onSubmit}>
        <div className="formDiv">
          <h1 className="helloText">Hello!</h1>
          <h2 className="createNewText">Create new account</h2>
          <div className="inputDiv">
            <input
              className="infoInput"
              type="text"
              placeholder="Email"
              onChange={onChange}
              value={registerForm.email}
              name="email"
            />
          </div>
          <div className="passwordDiv">
            <input
              className="passwordInput"
              type="password"
              placeholder="Password"
              onChange={onChange}
              value={registerForm.password}
              name="password"
            />
            <input
              className="passwordInput"
              type="password"
              placeholder="Confirm password"
              onChange={onChange}
              value={registerForm.confirmPassword}
              name="confirmPassword"
            />
          </div>
          <p className="errorMessage">{errorMessage}</p>
          <div className="buttonDiv">
            <button className="inputButton" type="submit">
              Create new account!
            </button>
          </div>
          <div className="oauth">
            <p className="signUpText"> Or sign up with... </p>
            <GoogleLogin
              onSuccess={loginWithGoogle}
              onError={() => {
                console.log('Login Failed');
              }}
            />
          </div>
        </div>
        <div className="redirect">
          <p className="account">Already have account?</p>
          <a className="link" href="/users/login">
            Sign in
          </a>
        </div>
      </form>
    </div>
  );
}

export default RegisterForm;
