import { useNavigate } from 'react-router-dom';
import { useState } from 'react';
import { GoogleLogin } from '@react-oauth/google';
import '../styles/Login.css';
import useAuth from '../hooks/useAuth';
import { jwtDecode } from 'jwt-decode';
import { serverFetch } from '../hooks/serverUtils';

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
      hash: hash
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
        signIn();
      }
    } catch (error) {
      console.log(error);
    }
  }

  async function onSubmit(e) {
    e.preventDefault();

    try {
      const saltRounds = 10;
      const salt = await bcrypt.genSalt(saltRounds);
      const hash = await bcrypt.hash(loginForm.password, salt);
      await loginUser(hash);
    } catch (err) {
      console.error('Error processing password:', err);
    }
  }

  function loginWithGoogle(response) {
    const { credential } = response;

    const userInfo = jwtDecode(credential);
    const userName = userInfo.name;
    const userEmail = userInfo.email;
    const userProfilePicture = userInfo.picture;
    const userGoogleId = userInfo.sub;
    console.log('User Info:', {
      userName,
      userEmail,
      userProfilePicture,
      userGoogleId
    });

    // TODO: If user already exists, redirect to home page. Elsewhere, redirect
    // to profile to set up profile.

    // TODO: Save: user Google id, email address, name, profile picture url,
    // access token / refresh token, login method = google, created at
    // This data can be retrieved from the google access token

    // Send data to /login/login for login, /login/register for register

    signInWithGoogle(credential);
    navigate('/users/home');
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
