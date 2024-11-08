import { createContext, useState, useEffect } from 'react';
import PropTypes from 'prop-types';

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [isSignedIn, setIsSignedIn] = useState(false);

  useEffect(() => {
    const token = localStorage.getItem('access_token');
    if (!token) {
      // Not logged in
      return;
    }

    // TODO: Check which user it is and if it is valid and log in properly
    setIsSignedIn(true);
  }, []);

  const signInWithGoogle = (credential) => {
    localStorage.setItem('is_logged_in_with_google', 'true');
    localStorage.setItem('access_token', credential);
    signIn();
  };

  const signIn = () => {
    // Maybe track this in the database
    setIsSignedIn(true);
  };
  const signOut = () => {
    // Maybe track this in the database
    setIsSignedIn(false);
  };

  return (
    <AuthContext.Provider
      value={{ isSignedIn, signInWithGoogle, signIn, signOut }}
    >
      {children}
    </AuthContext.Provider>
  );
};

AuthProvider.propTypes = {
  children: PropTypes.node.isRequired
};

export default AuthContext;
