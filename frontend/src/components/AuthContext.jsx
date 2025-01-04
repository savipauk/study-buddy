import { createContext, useState, useEffect } from 'react';
import PropTypes from 'prop-types';

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [isSignedIn, setIsSignedIn] = useState(false);
  const [isProfileSetupComplete, setIsProfileSetupComplete] = useState(true);

  useEffect(() => {
    const token = localStorage.getItem('access_token');
    if (!token) {
      // Not logged in
      return;
    }

    // TODO: Check which user it is and if it is valid and log in properly
    setIsSignedIn(true);
  }, []);

  const signInWithGoogle = (credential, email) => {
    localStorage.setItem('is_logged_in_with_google', 'true');
    localStorage.setItem('access_token', credential);
    localStorage.setItem('user_email', email);
    signIn();
  };

  const signIn = (info) => {
    // Maybe track this in the database
    localStorage.setItem('user_email', info);
    setIsSignedIn(true);
  };
  const signOut = () => {
    // Maybe track this in the database
    setIsSignedIn(false);
    localStorage.clear();
  };

  return (
    <AuthContext.Provider
      value={{
        isSignedIn,
        signInWithGoogle,
        signIn,
        signOut,
        isProfileSetupComplete,
        setIsProfileSetupComplete
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};

AuthProvider.propTypes = {
  children: PropTypes.node.isRequired
};

export default AuthContext;
