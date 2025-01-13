import { GoogleMap, LoadScriptNext } from '@react-google-maps/api';
import '../styles/ActiveGroups.css';
import CustomAdvancedMarker from './CustomAdvancedMarker';
import PropTypes from 'prop-types';

const librariesHardcode = ['places', 'marker'];

function StudyGroupInfo({ group, onClose }) {
  const mapLocation = {
    lat: parseFloat(group.xCoordinate),
    lng: parseFloat(group.yCoordinate)
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
            <label>{'StudyGroup'}</label>
            <h2>{group.groupName}</h2>
          </div>
        </div>
        <div className="infoGroup">
          <div className="lablesWrapper">
            <label>Organizator</label>
          </div>
          <div className="lablesWrapperLesson">
            <label>{group.username}</label>
          </div>
          <div className="lablesWrapper">
            <i className="fa-regular fa-user"></i>
          </div>
        </div>
        <div className="infoGroup">
          <div className="lablesWrapper">
            <label>Datum</label>
          </div>
          <div className="lablesWrapperLesson">
            <label>{group.date}</label>
          </div>
        </div>
        <div className="infoGroup">
          <div className="lablesWrapper">
            <label>Vrijeme</label>
          </div>
          <div className="lablesWrapperLesson">
            <label>{group.time}</label>
          </div>
        </div>
        <div className="infoGroup">
          <div className="lablesWrapper">
            <label>Opis</label>
          </div>
          <div className="aboutLableWrapper">
            <label>{group.description}</label>
          </div>
        </div>
        <div className="joinGroupButton">
          <button>Pridruzi se!</button>
        </div>
      </div>
      <div className="mapsLocation">
        <div className="locationName">
          <label>Lokacija:</label>
          <label>{group.location}</label>
        </div>
        <div className="maps">
          <LoadScriptNext
            googleMapsApiKey={import.meta.env.VITE_GOOGLE_MAPS_API}
            libraries={librariesHardcode}
          >
            <GoogleMap
              center={mapLocation}
              zoom={15}
              options={{
                mapId: import.meta.env.VITE_GOOGLE_MAPS_MAPID,
                streetViewControl: false,
                mapTypeControl: false
              }}
              mapContainerStyle={{ width: '100%', height: '100%' }}
            >
              <CustomAdvancedMarker
                lat={group.xCoordinate}
                lng={group.yCoordinate}
              />
            </GoogleMap>
          </LoadScriptNext>
        </div>
      </div>
    </div>
  );
}

StudyGroupInfo.propTypes = {
  group: PropTypes.shape({
    username: PropTypes.string,
    date: PropTypes.string,
    description: PropTypes.string,
    xCoordinate: PropTypes.string,
    yCoordinate: PropTypes.string,
    time: PropTypes.string,
    location: PropTypes.string,
    type: PropTypes.string,
    maxMembers: PropTypes.string,
    groupName: PropTypes.string
  }).isRequired,
  onClose: PropTypes.func.isRequired
};

export default StudyGroupInfo;
