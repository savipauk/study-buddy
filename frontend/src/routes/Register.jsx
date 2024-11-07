import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { GoogleLogin } from '@react-oauth/google';
import "../styles/Login.css";
import useAuth from "../hooks/useAuth";
import { serverFetch } from "../hooks/serverUtils";
import bcrypt from "bcryptjs";

function RegisterForm() {
  // TODO: If already signed in, redirect to home page

  const { signIn, signInWithGoogle } = useAuth();
  const navigate = useNavigate();

  const [registerForm, setRegisterForm] = useState({
    email: "",
    password: "",
    confirmPassword: "",
  });
  const [errorMessage, setErrorMessage] = useState("");

  function onChange(event) {
    const { name, value } = event.target;
    setRegisterForm((oldForm) => ({ ...oldForm, [name]: value }));
  }

  function loginWithGoogle(response) {
    const { credential } = response;
    // TODO: If user already exists, redirect to home page. Elsewhere, redirect
    // to profile to set up profile.

    // TODO: Save: user Google id, email address, name, profile picture url,
    // access token / refresh token, login method = google, created at
    // This data can be retrieved from the google access token

    // Send data to /login/login for login, /login/register for register

    signInWithGoogle(credential);
    navigate("/");
  }

  async function storeUserToDatabase(hash) {

    const data = {
      email: registerForm.email,
      firstName: "",
      lastName: "",
      hash: hash,
      studyRole: "STUDENT",
    }

    const endpoint = "/login/register"
    const options = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data),
    };

    try {
      const response = await serverFetch(endpoint, options);
      if (response.ok) {
        const data = await response.json();
        console.log(data)
        signIn()
      }
    } catch (error) {
      console.error(error)
    }
  }

  async function onSubmit(e) {
    e.preventDefault();

    if (!isValid()) {
      return;
    }

    try {
      const saltRounds = 10
      const salt = await bcrypt.genSalt(saltRounds)
      const hash = await bcrypt.hash(registerForm.password, salt)
      await storeUserToDatabase(hash)
    } catch (err) {
      console.error("Error processing password:", err)
    }
  }

  function isValid() {
    if (registerForm.password !== registerForm.confirmPassword) {
      setErrorMessage("Passwords do not match!");
      return false;
    }
    setErrorMessage("");
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
          <p> Or sign up with... </p>
          <GoogleLogin
            onSuccess={loginWithGoogle}
            onError={() => {
              console.log('Login Failed');
            }}
          />
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
