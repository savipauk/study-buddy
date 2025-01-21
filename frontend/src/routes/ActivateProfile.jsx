import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { serverFetch } from '../hooks/serverUtils';
import '../styles/ActivateProfile.css';

function ActivateProfile() {
  const navigate = useNavigate();
  const [userEmail, setUserEmail] = useState('');

  useEffect(() => {
    const email = localStorage.getItem('user_email');
    if (email) {
      setUserEmail(email);
    } else {
      console.error('User email not found in LocalStorage.');
      navigate('/');
    }
  }, [navigate]);

  const handleActivate = async () => {
    const endpoint = `/users/profile/activate/${userEmail}`;
    const options = {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' }
    };

    try {
      const response = await serverFetch(endpoint, options);
      if (response.ok) {
        console.log('Profile successfully activated.');
        navigate('/users/home');
      } else {
        console.error('Failed to activate profile.');
      }
    } catch (error) {
      console.error('Error during activation:', error);
    }
  };

  return (
    <div className="wholePage">
      <div className="activateWrapper">
        <h1>Aktivacija profila</h1>
        <p>
          Vaš profil nedavno je deaktiviran. Želite li ga ponovo aktivirati?
        </p>
        <button onClick={handleActivate}>Aktivirajte profil</button>
      </div>
    </div>
  );
}

export default ActivateProfile;
