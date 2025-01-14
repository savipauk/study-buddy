/*global google */

import { GoogleMap, LoadScriptNext } from '@react-google-maps/api';
import '../styles/ActiveGroups.css';
import CustomAdvancedMarker from './CustomAdvancedMarker';
import PropTypes from 'prop-types';
import { useState } from 'react';
import useAuth from '../hooks/useAuth';
import { useNavigate } from 'react-router-dom';
import ProfessorProfile from './ProfessorProfile';

const librariesHardcode = ['places', 'marker'];

function LessonInfo({ lesson, onClose }) {
  const navigate = useNavigate();
  const mapLocation = {
    lat: parseFloat(lesson.xCoordinate),
    lng: parseFloat(lesson.yCoordinate)
  };
  const [locationName, setLocationName] = useState('');
  const [showProfile, setShowProfile] = useState(false);
  const { setMyProfile, setUsername } = useAuth();
  const getLocation = () => {
    const geocoder = new google.maps.Geocoder();
    geocoder.geocode({ location: mapLocation }, (results, status) => {
      if (status === 'OK') {
        if (results[0]) {
          setLocationName(results[0].formatted_address);
        }
      }
    });
  };

  const handleProfileClick = () => {
    setShowProfile(true);
  };

  const handleCloseProfile = () => {
    setShowProfile(false);
  };

  return (
    <div className="groupInfoWrapper">
      <div className="exit">
        <button onClick={onClose}>
          <i className="fa-regular fa-circle-xmark"></i>
        </button>
      </div>
      <div className="allGroupInfoWrapper">
        <div className="infoGroup">
          <div className="groupTitle">
            <label>{'Instrukcije'}</label>
            <h2>{lesson.subject}</h2>
          </div>
        </div>
        <div className="infoGroup">
          <div className="lablesWrapper">
            <label>Organizator</label>
          </div>
          <div className="lablesWrapperLesson">
            <label>{lesson.username}</label>
          </div>
          <div className="lablesWrapper">
            <button className="profileInfo" onClick={handleProfileClick}>
              <i className="fa-regular fa-user"></i>
            </button>
          </div>
        </div>
        <div className="infoGroup">
          <div className="lablesWrapper">
            <label>Datum</label>
          </div>
          <div className="lablesWrapperLesson">
            <label>{lesson.date}</label>
          </div>
        </div>
        <div className="infoGroup">
          <div className="lablesWrapper">
            <label>Vrijeme</label>
          </div>
          <div className="lablesWrapperLesson">
            <label>{lesson.time}</label>
          </div>
        </div>
        <div className="infoGroup">
          <div className="lablesWrapper">
            <label>Cijena</label>
          </div>
          <div className="lablesWrapperLesson">
            <label>{lesson.price}</label>
          </div>
        </div>
        <div className="infoGroup">
          <div className="lablesWrapper">
            <label>Tip</label>
          </div>
          <div className="lablesWrapperLesson">
            <label>{lesson.type}</label>
          </div>
        </div>
        <div className="infoGroup">
          <div className="lablesWrapper">
            <label>Trajanje</label>
          </div>
          <div className="lablesWrapperLesson">
            <label>{lesson.duration}</label>
          </div>
        </div>
        {lesson.type === 'Masivne' && (
          <div className="infoGroup">
            <div className="lablesWrapper">
              <label>Maksimalan broj članova</label>
            </div>
            <div className="lablesWrapperLesson">
              <label>{lesson.maxMembers}</label>
            </div>
          </div>
        )}
        {lesson.type === 'Masivne' && (
          <div className="infoGroup">
            <div className="lablesWrapper">
              <label>Minimalan broj članova</label>
            </div>
            <div className="lablesWrapperLesson">
              <label>{lesson.minMembers}</label>
            </div>
          </div>
        )}
        <div className="joinGroupButton">
          <button>Pridružite se!</button>
        </div>
      </div>
      <div className="mapsLocation">
        <div className="locationName">
          <label>Lokacija:</label>
          <label>{locationName}</label>
        </div>
        <div className="maps">
          <LoadScriptNext
            googleMapsApiKey={import.meta.env.VITE_GOOGLE_MAPS_API}
            libraries={librariesHardcode}
          >
            <GoogleMap
              center={mapLocation}
              zoom={15}
              onLoad={getLocation}
              options={{
                mapId: import.meta.env.VITE_GOOGLE_MAPS_MAPID,
                streetViewControl: false,
                mapTypeControl: false
              }}
              mapContainerStyle={{ width: '100%', height: '100%' }}
            >
              <CustomAdvancedMarker
                lat={lesson.xCoordinate}
                lng={lesson.yCoordinate}
              />
            </GoogleMap>
          </LoadScriptNext>
        </div>
      </div>
      {showProfile && (
        <ProfessorProfile
          onClose={handleCloseProfile}
          username={group.username}
        />
      )}
    </div>
  );
}

LessonInfo.propTypes = {
  lesson: PropTypes.shape({
    username: PropTypes.string,
    date: PropTypes.string,
    duration: PropTypes.string,
    xCoordinate: PropTypes.string,
    yCoordinate: PropTypes.string,
    time: PropTypes.string,
    location: PropTypes.string,
    type: PropTypes.string,
    maxMembers: PropTypes.number,
    minMembers: PropTypes.number,
    price: PropTypes.number,
    subject: PropTypes.string
  }).isRequired,
  onClose: PropTypes.func.isRequired
};

export default LessonInfo;
