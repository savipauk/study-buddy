import { useState } from 'react'
import '../styles/Login.css'

function Root() {

  return (
    <>
      <RegisterForm></RegisterForm>
    </>
  )
}

function RegisterForm(){

	const [registerForm, setRegisterForm] = useState({username:'', email:'', password:'', confirmPassword: ''});
	const [errorMessage, setErrorMessage] = useState('');

	function onChange(event){
		const {name,value} = event.target
		setRegisterForm(oldForm => ({...oldForm,[name]:value}))
	}

	function onSubmit(e){

		e.preventDefault();
		
		if(!isValid()){
			return;
		}

		const data = {
			username: registerForm.username,
			email: registerForm.email,
			password: registerForm.password
		};

		const options = {
			method: 'POST',
			headers: {
				'Content-Type' : 'application/json'
			},
			body: JSON.stringify(data)
		};

		return fetch('/users/register', options);
	}

	function isValid(){
		if(registerForm.password !== registerForm.confirmPassword){
			setErrorMessage('Passwords do not match!');
			return false
		}
		setErrorMessage('');
		return true
	}


  return (
    <div className='formWrapper'>
      <form className="forms" onSubmit={onSubmit}>
        <div className='formDiv'>
          <h1 className='helloText'>Hello!</h1>
          <h2 className='createNewText'>Create new account</h2>
          <div className='inputDiv'>
            <input className='infoInput' type='text' placeholder='Username' onChange={onChange} value={registerForm.username} name='username'/>
            <input className='infoInput' type="text" placeholder='Email' onChange={onChange} value={registerForm.email} name='email'/>
          </div>
          <div className='passwordDiv'>
            <input className='passwordInput' type='password' placeholder='Password' onChange={onChange} value={registerForm.password} name='password'/>
            <input className='passwordInput' type='password' placeholder='Confirm password' onChange={onChange} value={registerForm.confirmPassword} name='confirmPassword'/>
          </div>
					<p className='errorMessage'>{errorMessage}</p>
          <div className='buttonDiv'>
            <button className='inputButton' type='submit'>Create new account!</button>
          </div>
        </div>
        <div className='redirect'>
          <p className='account'>Already have account?</p>
          <a className='link' href='/users/login'>Sing in</a>
      </div>
      </form>
      
    </div>
  );
}
export default Root
