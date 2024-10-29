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
        <div className='loginFormDiv'>
            <form className='loginForm'>
                <div>
                    <h1 className='helloText'>Hello!</h1>
                    <input className='usernameInput' placeholder='Username'/>
                    <input className='passwordInput' placeholder='Password'/>
                    <button className='signInButton'>Sign In</button>
                    <p>Dont have account?</p>
                    <a href='/register'>Register!</a>
                </div>
                
            </form>
        </div>
        </>
    )
}
export default Root
