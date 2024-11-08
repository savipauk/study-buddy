import '../styles/Root.css';
import { useNavigate } from 'react-router-dom';
import useAuth from '../hooks/useAuth';
import { googleLogout } from '@react-oauth/google';
import { useEffect } from 'react';

function Root() {
  const { isSignedIn } = useAuth();
  const navigate = useNavigate();

  useEffect(() => {
    if (isSignedIn) {
      navigate('users/home');
    }
  }, [isSignedIn, navigate]);

  return (
    <div className='welcomeWrapper'>
      <div className='title'>
        <h1 className='text'>STUDY BUDDY</h1>
      </div>
      <div className='description'>
        <p>Study smarter, together</p>
      </div>
      {isSignedIn ? (
        <>
          <p> TODO: Button for sign out </p>
          <SignOutButton />
        </>
      ) : (
        <div className='signUpButtonDiv'>
          <SignUpButton />
        </div>
      )}
    </div>
  );
}

export function SignOutButton() {
  const { signOut } = useAuth();
  const isLoggedInWithGoogle = localStorage.getItem('is_logged_in_with_google');
  const navigate = useNavigate();

  const handleClick = () => {
    if (isLoggedInWithGoogle) {
      googleLogout();
      localStorage.removeItem('is_logged_in_with_google');
    }

    localStorage.removeItem('access_token');
    signOut();
    navigate('/');
  };

  return <button onClick={handleClick}>Sign out</button>;
}

function SignUpButton() {
  const navigate = useNavigate();

  const handleClick = () => {
    navigate('/users/login');
  };
  return (
    <button className='signUpButton' onClick={handleClick}>
      SIGN UP
    </button>
  );
}

export default Root;
