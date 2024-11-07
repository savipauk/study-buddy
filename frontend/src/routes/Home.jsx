import { useNavigate } from 'react-router-dom';
import { useState } from 'react';
import { useEffect } from 'react';
import { serverFetch } from '../hooks/serverUtils';
import { SignOutButton } from './Root';
import '../styles/HomePage.css';

function HomePage() {
  return (
    <div className="homePageWrapper">
      <Header></Header>
      <h1 className="someText">HOMEPAGE</h1>
    </div>
  );
}

function Header() {
  return (
    <div className="header">
      <ProfileButton></ProfileButton>
      <SignOutButton></SignOutButton>
    </div>
  );
}

function ProfileButton() {
  const navigate = useNavigate();

  const handleClick = () => {
    navigate('/users/profile');
  };

  return (
    <button onClick={handleClick} className="profileButton">
      <i class="fa-solid fa-user"></i>
    </button>
  );
}
export default HomePage;
