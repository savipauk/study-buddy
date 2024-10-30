import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import {
  createBrowserRouter,
  RouterProvider,
} from "react-router-dom"
import Root from'./routes/Root'
import './index.css'
import ErrorPage from './ErrorPage'
import Register from './routes/Register'
import Login from './routes/Login'

const router = createBrowserRouter([
  {
    path: "/",
    element: <Root />,
    errorElement: <ErrorPage />,
  },
  {
    path: "/users/login",
    element: <Login />,
    errorElement: <ErrorPage />,
  },
  {
    path: '/users/register',
    element: <Register />,
    errorElement: <ErrorPage />,
  }
]);

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <RouterProvider router={router} />
  </StrictMode>,
)
