import { useNavigate } from 'react-router-dom';
import { useState } from 'react';
import { useEffect } from 'react';
import { serverFetch } from '../hooks/serverUtils';
import { SignOutButton } from './Root';

function HomePage() {
  return (
    <>
      <h1>HOMEPAGE</h1>
      <SignOutButton></SignOutButton>
    </>
  );
}

export default HomePage;
