import { useState } from 'react'
import reactLogo from '../assets/react.svg'
import viteLogo from '/vite.svg'
import '../styles/Root.css'

function Root() {
  const [count, setCount] = useState(0)

  return (
    <div className='welcomeWrapper'>
      <div className='title'>
        <h1>STUDY BUDDY</h1>
      </div>
      <div className='description'>
        <p>Study smarter, together</p>
      </div>
      <div className='signUpButtonDiv'>
        <SignUpButton />
      </div>
    </div>
  )
}

function SignUpButton(){
  return <button className='signUpButton'>SIGN UP</button>
}

export default Root
