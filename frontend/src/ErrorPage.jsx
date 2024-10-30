import { useRouteError } from "react-router-dom";
import './styles/Root.css'
export default function ErrorPage() {
  const error = useRouteError();
  console.error(error);

  return (
    <div id="error-page">
      <h1 className="er">Oops!</h1>
      <p className="er">Sorry, an unexpected error has occurred.</p>
      <p className="er">
        <i>{error.statusText || error.message}</i>
      </p>
    </div>
  );
}
