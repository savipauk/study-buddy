import { useState } from 'react'
import '../styles/Root.css'
import '../styles/Login.css'
import { GoogleLogin } from '@react-oauth/google';

function Signup() {
  const [formValues, setFormValues] = useState({
    email: '',
    firstName: '',
    lastName: '',
    password: '',
    confirmPassword: '',
  })

  const handleChange = (e) => {
    const { name, value } = e.target
    setFormValues((prevValues) => ({
      ...prevValues,
      [name]: value,
    }))
  }

  const HandleSignup = async () => {

  }

  const LoginWithGoogle = async (response) => {
    const { credential } = response;
    localStorage.setItem("access_token", credential)

    // Send to ../oauth/login ?

    setLoggedIn(true)
  }

  return (
    <>
      <form className="card inputs">
        <label>Email</label>
        <input type="email" name="email" value={formValues.email} onChange={handleChange} />
        <label>First name</label>
        <input type="text" name="firstName" value={formValues.firstName} onChange={handleChange} />
        <label>Last name</label>
        <input type="text" name="lastName" value={formValues.lastName} onChange={handleChange} />
        <label>Password</label>
        <input type="password" name="password" value={formValues.password} onChange={handleChange} />
        <label>Confirm password</label>
        <input type="password" name="confirmPassword" value={formValues.confirmPassword} onChange={handleChange} />
        <button onClick={HandleSignup}>Sign me up!</button>
      </form>
      <p> Or sign up with... </p>
      <GoogleLogin
        onSuccess={LoginWithGoogle}
        onError={() => {
          console.log('Login Failed');
        }}
      />
      <br/>
      <br/>
      <a href="/"><button>Bring me back!</button></a>
    </>
  )
}

export default Signup

