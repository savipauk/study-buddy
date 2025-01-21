/*global google */
import { GoogleMap } from '@react-google-maps/api';
import '../styles/ActiveGroups.css';
import CustomAdvancedMarker from './CustomAdvancedMarker';
import PropTypes from 'prop-types';
import { useState } from 'react';
import StudentProfile from './StudentProfile';
import { serverFetch } from '../hooks/serverUtils';
import { useEffect } from 'react';

function StudyGroupInfo({ group, onClose, joinedGroups, onLeave }) {
  const [showProfile, setShowProfile] = useState(false);
  const mapLocation = {
    lat: parseFloat(group.xCoordinate),
    lng: parseFloat(group.yCoordinate)
  };
  const [locationName, setLocationName] = useState();
  const [materials, setMaterials] = useState([]);

  const [joinedGroupsState, setJoinedGroups] = useState(joinedGroups);
  const [joined, setJoined] = useState(
    joinedGroupsState.includes(group.studyGroupId)
  );
  const [currentNumberOfMembers, setCurrentNumberOfMembers] = useState(
    group.currentNumberOfMembers
  );

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
    try {
      const response = await joinGroup(group.studyGroupId, email);
      if (response.ok) {
        const data = await response.json();
        if (data.message === 'OK') {
          joinedGroups.push(group.studyGroupId);
          setJoinedGroups(joinedGroups);
          setJoined(true);
          setCurrentNumberOfMembers(currentNumberOfMembers + 1);
        } else if (data.message === 'GROUP_FULL') alert('Grupa popunjena');
      } else {
        console.log('Error while fetching');
      }
    } catch (error) {
      console.log(error);
    }
  };

  const handleLeaveGroup = async () => {
    const email = localStorage.getItem('user_email');
    try {
      const response = await leaveGroup(group.studyGroupId, email);
      if (response.ok) {
        const updatedGroups = joinedGroups.filter(
          (id) => id !== group.studyGroupId
        );
        joinedGroups.splice(0, joinedGroups, ...updatedGroups);
        setJoinedGroups(joinedGroups);
        setJoined(false);
        setCurrentNumberOfMembers(currentNumberOfMembers - 1);
        onLeave(group.studyGroupId, 'group');
      } else {
        console.log('Error while fetching');
      }
    } catch (error) {
      console.log(error);
    }
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
      return response;
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
      return response;
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
            <div className="currentMembers">
              <label>{'StudyGroup'}</label>
              <label>
                Broj prijavljenih : {currentNumberOfMembers}/{group.maxMembers}
              </label>
            </div>
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
                <li key={material.materialId}>
                  <a
                    href={`data:${material.mimeType};base64,${material.fileData}`}
                    download={material.fileName}
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
    studyGroupId: PropTypes.number,
    currentNumberOfMembers: PropTypes.number
  }).isRequired,
  onClose: PropTypes.func.isRequired,
  joinedGroups: PropTypes.array.isRequired,
  onLeave: PropTypes.func.isRequired
};

export default StudyGroupInfo;
