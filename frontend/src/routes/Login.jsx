import { useNavigate } from 'react-router-dom';
import { useState } from 'react';
import { GoogleLogin } from '@react-oauth/google';
import '../styles/Login.css';
import useAuth from '../hooks/useAuth';
import { getHash, serverFetch } from '../hooks/serverUtils';

function LoginForm() {
  const [loginForm, setLoginForm] = useState({ username: '', password: '' });
  const navigate = useNavigate();
  const { signIn, signInWithGoogle } = useAuth();

  function onChange(event) {
    const { name, value } = event.target;
    setLoginForm((oldForm) => ({ ...oldForm, [name]: value }));
  }

  async function loginUser(hash) {
    const data = {
      username: loginForm.username,
      hashedPassword: hash
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
        signIn();
      }
    } catch (error) {
      console.log(error);
    }
  }

  async function onSubmit(e) {
    e.preventDefault();

    try {
      const hash = await getHash(loginForm.password);
      await loginUser(hash);
    } catch (err) {
      console.error('Error processing password:', err);
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

  return (
    <>
      <div className="formWrapper">
        <form className="forms" onSubmit={onSubmit}>
          <div className="formDiv">
            <h1 className="helloText">Hello!</h1>
            <div className="inputDiv">
              <input
                className="infoInput"
                placeholder="Username"
                name="username"
                onChange={onChange}
                value={loginForm.username}
              />
              <input
                className="passwordInput"
                placeholder="Password"
                name="password"
                onChange={onChange}
                type="password"
                value={loginForm.password}
              />
            </div>
            <div className="buttonDiv">
              <button className="inputButton" type="submit">
                Sign In
              </button>
            </div>
            <div className="redirect">
              <p className="account">Dont have account?</p>
              <a href="/users/register" className="link">
                Register here
              </a>
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
        </form>
      </div>
    </>
  );
}
export default LoginForm;
