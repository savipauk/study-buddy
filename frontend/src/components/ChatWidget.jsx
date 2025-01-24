import { useEffect, useState } from 'react';
import '../styles/ChatWidget.css';
import PropTypes from 'prop-types';

let isScriptLoaded = false;
let scriptLoadingPromise = null;

function ChatWidget({ text, number, email, image }) {
  const [isWidgetLoaded, setIsWidgetLoaded] = useState(false);

  useEffect(() => {
    let isMounted = true;
    const loadScript = () => {
      if (isScriptLoaded) {
        return Promise.resolve();
      }
      if (!scriptLoadingPromise) {
        scriptLoadingPromise = new Promise((resolve, reject) => {
          const script = document.createElement('script');
          script.src = 'https://popupsmart.com/freechat.js';
          script.async = true;

          script.onload = () => {
            isScriptLoaded = true;
            resolve();
          };

          script.onerror = () => {
            console.error('Failed to load PopupSmart FreeChat script.');
            reject(new Error('Failed to load PopupSmart FreeChat script.'));
          };

          document.body.appendChild(script);
        });
      }

      return scriptLoadingPromise;
    };

    const initializeWidget = () => {
      if (window.start && typeof window.start.init === 'function') {
        window.start.init({
          title: 'StudyBuddy Chat',
          message: text,
          color: '#1C86FA',
          position: 'right',
          placeholder: 'Enter your message',
          withText: 'Write with',
          viaWhatsapp: 'Or write us directly via Whatsapp',
          gty: 'Go to your',
          awu: 'and write us',
          connect: 'Connect now',
          button: 'Write us',
          device: 'everywhere',
          logo: 'https://cdn.pixabay.com/photo/2016/09/16/09/20/books-1673578_1280.png',
          person: image,
          services: [
            { name: 'mail', content: email },
            { name: 'whatsapp', content: number }
          ]
        });

        if (isMounted) {
          setIsWidgetLoaded(true);
        }
      } else {
        console.error('PopupSmart widget failed to initialize.');
      }
    };

    loadScript()
      .then(() => {
        initializeWidget();
      })
      .catch((error) => {
        console.error('Error loading the chat widget:', error);
      });

    return () => {
      isMounted = false;
      const widgetElement = document.getElementById('freechatpopup');
      if (widgetElement) {
        widgetElement.remove();
      }
      if (window.start && typeof window.start.destroy === 'function') {
        window.start.destroy();
      }

      setIsWidgetLoaded(false);
    };
  }, [text, number, email, image]);

  useEffect(() => {
    if (isWidgetLoaded) {
      const chatWidget = document.querySelector('#freechatpopup');
      if (chatWidget) {
        chatWidget.style.opacity = 1;
      }
    }
  }, [isWidgetLoaded, text]);

  return null;
}

ChatWidget.propTypes = {
  text: PropTypes.string.isRequired,
  number: PropTypes.string.isRequired,
  email: PropTypes.string.isRequired,
  image: PropTypes.string.isRequired
};

export default ChatWidget;
