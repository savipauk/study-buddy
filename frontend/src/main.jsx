import { StrictMode } from 'react'
import { GoogleOAuthProvider } from '@react-oauth/google';
import { createRoot } from 'react-dom/client'
import {
  createBrowserRouter,
  RouterProvider,
} from "react-router-dom"
import Root from './routes/Root'
import './index.css'
import ErrorPage from './ErrorPage'
import Signup from './routes/Signup'

const router = createBrowserRouter([
  {
    path: "/",
    element: <Root />,
    errorElement: <ErrorPage />,
  },
  {
    path: "/signup",
    element: <Signup />,
  },
]);

createRoot(document.getElementById('root')).render(
  <GoogleOAuthProvider clientId='4143611273-h8v79jdefdqr65l0n23efpg84r5vospr.apps.googleusercontent.com'>
    <StrictMode>
      <RouterProvider router={router} />
    </StrictMode>
  </GoogleOAuthProvider>
)
