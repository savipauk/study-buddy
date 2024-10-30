import { useState } from 'react'
import '../styles/Root.css'
import { useNavigate } from 'react-router-dom'

function Root() {
  const [count, setCount] = useState(0)

  return (
    <div className='welcomeWrapper'>
      <div className='title'>
        <h1 className='text'>STUDY BUDDY</h1>
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
	
	const navigate = useNavigate()

	const handleClick = () => {
		navigate('/users/login')
	}
  return <button className='signUpButton' onClick={handleClick}>SIGN UP</button>
}

export default Root
