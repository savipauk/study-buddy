import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import { GoogleOAuthProvider } from '@react-oauth/google';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import Root from './routes/Root';
import './index.css';
import ErrorPage from './ErrorPage';
import Register from './routes/Register';
import Login from './routes/Login';
import HomePage from './routes/Home';
import Profile from './routes/Profile';
import { AuthProvider } from './components/AuthContext';

const router = createBrowserRouter([
  {
    path: '/',
    element: <Root />,
    errorElement: <ErrorPage />
  },
  {
    path: '/users/login',
    element: <Login />,
    errorElement: <ErrorPage />
  },
  {
    path: '/users/register',
    element: <Register />,
    errorElement: <ErrorPage />
  },
  {
    path: 'users/home',
    element: <HomePage />,
    errorElement: <ErrorPage />
  },
  {
    path: 'users/profile',
    element: <Profile />,
    errorElement: <ErrorPage />
  }
]);

createRoot(document.getElementById('root')).render(
  <GoogleOAuthProvider clientId="4143611273-h8v79jdefdqr65l0n23efpg84r5vospr.apps.googleusercontent.com">
    <AuthProvider>
      <StrictMode>
        <RouterProvider router={router} />
      </StrictMode>
    </AuthProvider>
  </GoogleOAuthProvider>
);
