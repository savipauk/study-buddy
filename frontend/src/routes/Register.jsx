import { useState } from 'react'
import '../styles/Login.css'

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
    <div className='formWrapper'>
      <form className="forms">
        <div className='formDiv'>
          <h1 className='helloText'>Hello!</h1>
          <h2 className='createNewText'>Create new account</h2>
          <div className='inputDiv'>
            <input className='infoInput' type='tex' placeholder='Username'/>
            <input className='infoInput' type="text" placeholder='Email'/>
          </div>
          <div className='passwordDiv'>
            <input className='passwordInput' type='text' placeholder='Password'/>
            <input className='passwordInput' type='text' placeholder='Confirm password'/>
          </div>
          <div className='buttonDiv'>
            <button className='inputButton' type='button'>Create new account!</button>
          </div>
        </div>
        <div className='redirect'>
          <p className='account'>Already have account?</p>
          <a className='link' href='/login'>Sing in</a>
      </div>
      </form>
      
    </div>
  );
}
export default Root
