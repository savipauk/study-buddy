import { useNavigate } from 'react-router-dom';
import { SignOutButton } from './Root';
import '../styles/HomePage.css';
import Header from '../components/Header';

function HomePage() {
  return (
    <div className="homePageWrapper">
      <Header></Header>
      <h1 className="someText">HOMEPAGE</h1>
    </div>
  );
}

export default HomePage;
