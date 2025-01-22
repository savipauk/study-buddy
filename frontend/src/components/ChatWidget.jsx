import { useEffect, useState } from 'react';
import '../styles/ChatWidget.css';

const ChatWidget = () => {
  const [isWidgetLoaded, setIsWidgetLoaded] = useState(false);

  useEffect(() => {
    // Check if script already exists to avoid duplicates
    if (!document.querySelector('script[src="https://popupsmart.com/freechat.js"]')) {
      const script = document.createElement('script');
      script.src = 'https://popupsmart.com/freechat.js';
      script.async = true;
      script.onload = () => {
        if (window.start && typeof window.start.init === 'function') {
          window.start.init({
            title: 'meow there✌️',
            message: 'meow meow meow meowmeow meowmeow ',
            color: '#1C86FA',// '#3a7bd5',
            position: 'right',
            placeholder: 'Enter your message',
            withText: 'Write with',
            viaWhatsapp: 'Or write us directly via Whatsapp',
            gty: 'Go to your',
            awu: 'and write us',
            connect: 'Connect now',
            button: 'Write us',
            device: 'everywhere',
            logo: 'https://d2r80wdbkwti6l.cloudfront.net/vd31ZlOLDRmvbjuSCffwzS4lFiwozkfC.jpg', // Study Buddy logo
            person: 'https://d2r80wdbkwti6l.cloudfront.net/IHhoZo5VWlGKjckykGd0igxyWEbKEQmY.jpg', // Profile picture of receiver
            services: [
              { name: 'mail', content: 'mijau@meow.com' }, // email of receiver
              { name: 'whatsapp', content: '+38512345678' } // Phone number of receiver
            ]
          });

          setIsWidgetLoaded(true);
        } else {
          console.error('PopupSmart widget failed to initialize.');
        }
      };

      document.body.appendChild(script);
    }
  }, []);

  useEffect(() => {
    if (isWidgetLoaded) {
      const chatWidget = document.querySelector('#freechatpopup');
      if (chatWidget) {
        chatWidget.style.opacity = 1;
      }
    }
  }, [isWidgetLoaded]);


  return null;
};

export default ChatWidget;

