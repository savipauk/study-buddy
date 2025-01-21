import ChatWidget from '../components/ChatWidget';
import '../styles/Root.css';



function Test() {

  return (
    <div className="welcomeWrapper">
      <div className="title">
        <h1 className="text">STUDY BUDDY</h1>
      </div>
      <div className="description">
        <p>Vrijeme je za pametno učenje</p>
      </div>
      <ChatWidget />
    </div>
  );
}

export default Test;
