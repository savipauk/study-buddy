import { useState } from 'react'
import '../styles/Login.css'

function Root() {
  const [count, setCount] = useState(0)

  return (
    <>
      <LoginForm />
    </>
  )
}

function LoginForm(){
    return (
        <>
        <div className='formWrapper'>
          <form className='forms'>
            <div className='formDiv'> 
              <h1 className='helloText'>Hello!</h1>
              <div className='inputDiv'>
                  <input className='infoInput' placeholder='Username'/>
                  <input className='passwordInput' placeholder='Password'/>
              </div>
              <div className='buttonDiv'>
                <button className='inputButton'>Sign In</button>
              </div> 
              <div className='redirect'>
                <p className='account'>Dont have account?</p>
                <a href='/register' className='link'>Register here</a>
              </div>
              
            </div>
          </form>
        </div>
        </>
    )
}
export default Root
