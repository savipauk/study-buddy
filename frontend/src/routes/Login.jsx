import { useState } from "react";
import "../styles/Login.css";

function Root() {
  const [count, setCount] = useState(0);

  return (
    <>
      <LoginForm />
    </>
  );
}

function LoginForm() {
  const [loginForm, setLoginForm] = useState({ username: "", password: "" });

  function onChange(event) {
    const { name, value } = event.target;
    setLoginForm((oldForm) => ({ ...oldForm, [name]: value }));
  }

  function onSubmit(e) {
    e.preventDefault();

    const data = {
      username: loginForm.username,
      password: loginForm.password,
    };

    const options = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data),
    };

    fetch("/users/login", options);
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
              <p className="or">OR</p>
              <button className="oauthButton">
                <i className="fab fa-google icon"></i> Use google account
              </button>
              <button className="oauthButton">
                <i className="fab fa-github icon"></i> Use github account
              </button>
            </div>
          </div>
        </form>
      </div>
    </>
  );
}
export default Root;
