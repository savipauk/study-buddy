import { useState, useEffect } from 'react'
import '../styles/Root.css'
import '../styles/Login.css'
import { GoogleLogin } from '@react-oauth/google';
import { googleLogout } from '@react-oauth/google';

function Login() {
  const [loggedIn, setLoggedIn] = useState(false)

  useEffect(() => {
    const token = localStorage.getItem("access_token");
    if (token) {
      setLoggedIn(true);
    }
  }, []);

  const LogOut = () => {
    googleLogout();
    localStorage.removeItem("access_token");
    setLoggedIn(false);
  };

  const Signup = async () => {
    
  }

  const LoginWithAccount = async () => {
    // Hash the password
    // Send to /login

    // Send the credential to your backend for verification and to establish a session
    // await fetch('http://localhost:8080/login', {
    //   method: 'POST',
    //   headers: {
    //     'Content-Type': 'application/json',
    //     'Access-Control-Allow-Origin': '*',
    //   },
    //   // body: JSON.stringify({ token: credential }),
    // });
    // Save token
  }

  const LoginWithGoogle = async (response) => {
    const { credential } = response;
    localStorage.setItem("access_token", credential)

    // Send to ../oauth/login ?

    setLoggedIn(true)
  }

  return (
    <>
      {loggedIn ? (
        <div>
          <button onClick={LogOut}>Log out</button>
        </div>
      ) : (
        <>
          <form className="card inputs">
            <input type="text" />
            <input type="password" />
            <button onClick={LoginWithAccount}>
              Login
            </button>
          </form>
          <a href="/signup">
            I don't have an account
          </a>
          <div className="card">
            <p> Or sign in with... </p>
            <GoogleLogin
              onSuccess={LoginWithGoogle}
              onError={() => {
                console.log('Login Failed');
              }}
            />
          </div>
        </>
      )
      }
    </>
  )
}

export default Login
