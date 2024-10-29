import { useState } from 'react'
import '../styles/Register.css'

function Root() {
  const [count, setCount] = useState(0)

  return (
    <>
      <RegisterForm></RegisterForm>
    </>
  )
}

function RegisterForm(){

  return (
    <div className='formDiv'>
      <form className="registerForm">
        <div className='registerFormDiv'>
          <h1 className='helloText'>Hello!</h1>
          <h2 className='createNewText'>Create new account</h2>
          <div className='registerInputDiv'>
            <input className='registerInput' type='tex' placeholder='Username'/>
            <input className='registerInput' type="text" placeholder='Email'/>
          </div>
          <div className='passwordDiv'>
            <input className='registerInputPass' type='text' placeholder='Password'/>
            <input className='registerInputPass' type='text' placeholder='Confirm password'/>
          </div>
          <div className='buttonDiv'>
            <button className='registerInputButton' type='button'>Create new account!</button>
          </div>
        </div>
        <div className='redirect'>
          <p className='hasAccount'>Already have account?</p>
          <a className='redirectToSignIn' href='/login'>Sing in</a>
      </div>
      </form>
      
    </div>
  );
}
export default Root
