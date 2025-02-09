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
import ProfileInfo from './routes/ProfileInfo';
import AdminPage from './routes/Admin';
import ActivateProfile from './routes/ActivateProfile';
import { LoadScriptNext } from '@react-google-maps/api';

const librariesHardcode = ['places', 'marker'];

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
    path: 'users/profile/edit',
    element: <Profile />,
    errorElement: <ErrorPage />
  },
  {
    path: 'users/profile/:username',
    element: <ProfileInfo />,
    errorElement: <ErrorPage />
  },
  {
    path: 'admin',
    element: <AdminPage />,
    errorElement: <ErrorPage />
  },
  {
    path: '/activateProfile',
    element: <ActivateProfile />,
    errorElement: <ErrorPage />
  }
]);

createRoot(document.getElementById('root')).render(
  <GoogleOAuthProvider clientId={import.meta.env.VITE_GOOGLE_CLIENT_ID}>
    <LoadScriptNext
      googleMapsApiKey={import.meta.env.VITE_GOOGLE_MAPS_API}
      libraries={librariesHardcode}
    >
      <AuthProvider>
        <StrictMode>
          <RouterProvider router={router} />
        </StrictMode>
      </AuthProvider>
    </LoadScriptNext>
  </GoogleOAuthProvider>
);
