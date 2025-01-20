/*global google */
import { GoogleMap } from '@react-google-maps/api';
import '../styles/ActiveGroups.css';
import CustomAdvancedMarker from './CustomAdvancedMarker';
import PropTypes from 'prop-types';
import { useState } from 'react';
import StudentProfile from './StudentProfile';
import { serverFetch } from '../hooks/serverUtils';
import { useEffect } from 'react';

function StudyGroupInfo({ group, onClose, joinedGroups }) {
  const [showProfile, setShowProfile] = useState(false);
  const mapLocation = {
    lat: parseFloat(group.xCoordinate),
    lng: parseFloat(group.yCoordinate)
  };
  const [locationName, setLocationName] = useState();
  const [materials, setMaterials] = useState([]);

  const joined = joinedGroups.includes(group.studyGroupId);

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

  useEffect(() => {
    const fetchMaterials = async () => {
      try {
        const response = await serverFetch(
          `/material/group/${group.studyGroupId}`
        );
        if (response.ok) {
          const data = await response.json();
          setMaterials(data);
        } else {
          console.error('Failed to fetch materials');
        }
      } catch (error) {
        console.error('Error fetching materials:', error);
      }
    };

    fetchMaterials();
  }, [group.studyGroupId]);

  const handleProfileClick = () => {
    setShowProfile(true);
  };

  const handleCloseProfile = () => {
    setShowProfile(false);
  };

  const handleJoinGroup = async () => {
    const email = localStorage.getItem('user_email');
    await joinGroup(group.studyGroupId, email);
  };

  const handleLeaveGroup = async () => {
    const email = localStorage.getItem('user_email');
    await leaveGroup(group.studyGroupId, email);
  };

  async function joinGroup(id, username) {
    const endpoint = `/studyGroup/${id}/add-student/${username}`;
    const options = {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      }
    };

    try {
      const response = await serverFetch(endpoint, options);
      if (!response.ok) {
        console.log('Error while fetching');
      }
    } catch (error) {
      console.log(error);
    }
  }

  async function leaveGroup(id, username) {
    const endpoint = `/studyGroup/${id}/remove-student/${username}`;
    const options = {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json'
      }
    };

    try {
      const response = await serverFetch(endpoint, options);
      if (!response.ok) {
        console.log('Error while fetching');
      }
    } catch (error) {
      console.log(error);
    }
  }

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
        <div className="infoGroup">
          <label>Priložene datoteke</label>
          {materials.length > 0 ? (
            <ul>
              {materials.map((material) => (
                <li key={material.id}>
                  <a
                    href={`/material/download/${material.id}`}
                    target="_blank"
                    rel="noopener noreferrer"
                  >
                    {material.fileName}
                  </a>
                </li>
              ))}
            </ul>
          ) : (
            <p>Nema priloženih datoteka.</p>
          )}
        </div>
        <div className="joinGroupButton">
          {!joined && <button onClick={handleJoinGroup}>Pridružite se!</button>}
          {joined && <button onClick={handleLeaveGroup}>Napusti grupu!</button>}
        </div>
      </div>
      <div className="mapsLocation">
        <div className="locationName">
          <label>Lokacija:</label>
          <label>{locationName}</label>
        </div>
        <div className="maps">
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
              lat={group.xCoordinate}
              lng={group.yCoordinate}
            />
          </GoogleMap>
        </div>
      </div>
      {showProfile && (
        <StudentProfile
          onClose={handleCloseProfile}
          username={group.username}
        />
      )}
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
    maxMembers: PropTypes.number,
    groupName: PropTypes.string,
    studyGroupId: PropTypes.number
  }).isRequired,
  onClose: PropTypes.func.isRequired,
  joinedGroups: PropTypes.array.isRequired
};

export default StudyGroupInfo;
