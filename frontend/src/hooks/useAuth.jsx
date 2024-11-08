import { useContext } from 'react';
import AuthContext from '../components/AuthContext';

const useAuth = () => useContext(AuthContext);
export default useAuth;
