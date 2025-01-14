import { useRouteError } from 'react-router-dom';
import './styles/Root.css';

export default function ErrorPage() {
  const error = useRouteError();
  console.error(error);

  return (
    <div id="error-page">
      <h1 className="er">Ups!</h1>
      <p className="errorText">Oprostite, dogodila se neočekivana pogreška.</p>
      <p className="errorText">
        <i>{error.statusText || error.message}</i>
      </p>
    </div>
  );
}
