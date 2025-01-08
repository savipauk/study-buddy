/* global google */

import { useState, useRef, useEffect } from 'react';
import {
  GoogleMap,
  LoadScriptNext,
  StandaloneSearchBox
} from '@react-google-maps/api';
import '../styles/StudyGroupLessons.css';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import { serverFetch } from '../hooks/serverUtils';
import PropTypes from 'prop-types';

const librariesHardcode = ['places', 'marker'];

function CreateStudyGroupForm({ onClose }) {
  const mapLocation = {
    //Zagreb
    lat: 45.815,
    lng: 15.9819
  };
  const [selectedDate, setSelectedDate] = useState(null);
  const [date, setDate] = useState(null);
  const [time, setTime] = useState(null);
  const [validationMessage, setValidationMessage] = useState('');
  const [location, setLocation] = useState(mapLocation);
  const [locationName, setLocationName] = useState(null);
  const [maxNum, setMaxNum] = useState(0);
  const mapRef = useRef(null);
  const searchBoxRef = useRef(null);
  const markerRef = useRef(null);
  const [groupInfoForm, setGroupInfoForm] = useState({
    name: '',
    description: ''
  });

  function onChange(event) {
    const { name, value } = event.target;
    setGroupInfoForm((oldForm) => ({ ...oldForm, [name]: value }));
  }

  useEffect(() => {
    if (mapRef.current && !markerRef.current) {
      const { AdvancedMarkerElement } = google.maps.marker;

      markerRef.current = new AdvancedMarkerElement({
        position: location,
        map: mapRef.current,
        gmpDraggable: true
      });
      markerRef.current.draggable = true;

      markerRef.current.addListener('dragend', (event) => {
        const newLocation = {
          lat: event.latLng.lat(),
          lng: event.latLng.lng()
        };
        setLocation(newLocation);
      });
    } else if (markerRef.current) {
      markerRef.current.position = location;
    }
  }, [location]);

  const handlePlaceSelect = () => {
    const place = searchBoxRef.current.getPlaces()[0];
    if (place && place.geometry) {
      const newLocation = {
        lat: place.geometry.location.lat(),
        lng: place.geometry.location.lng()
      };
      setLocation(newLocation);
      setLocationName(place.name);
      mapRef.current.panTo(newLocation);
    }
  };

  const changeParticipants = (id) => {
    if (id === 0 && maxNum > 0) {
      setMaxNum(maxNum - 1);
    } else if (id === 1) {
      setMaxNum(maxNum + 1);
    }
  };

  const handleDateTimeSelect = (dateTime) => {
    setSelectedDate(dateTime);
    const date = dateTime.toISOString().split('T')[0];
    const time = dateTime.toTimeString().split(' ')[0];
    setDate(date);
    setTime(time);
  };

  const isValid = () => {
    if (!groupInfoForm.name.trim()) {
      setValidationMessage('Naziv je obavezan');
      return false;
    }
    if (!groupInfoForm.description.trim()) {
      setValidationMessage('Opis je obavezan');
      return false;
    }
    if (!selectedDate) {
      setValidationMessage('Molimo odaberite datum i vrijeme');
      return false;
    }
    if (maxNum <= 0) {
      setValidationMessage('Broj članova mora biti veći od 0');
      return false;
    }
    if (!location.lat || !location.lng) {
      setValidationMessage('Molimo odaberite lokaciju');
      return false;
    }
    return true;
  };

  async function onSubmit() {
    if (!isValid()) {
      return;
    } else {
      await createStudyGroup();
      onClose();
    }
  }

  async function createStudyGroup() {
    const email = localStorage.getItem('user_email');
    const endpoint = '/studyGroup/create';
    const data = {
      email: email,
      groupName: groupInfoForm.name,
      description: groupInfoForm.description,
      maxMembers: maxNum,
      xCoordinate: location.lat,
      yCoordinate: location.lng,
      location: locationName,
      date: date,
      time: time
    };
    const options = {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(data)
    };

    try {
      const response = await serverFetch(endpoint, options);
      if (response.ok) {
        onClose();
      } else {
        console.log('Failed to fetch data', response.statusText);
        return null;
      }
    } catch (error) {
      console.log(error);
      return null;
    }
  }

  return (
    <div className="wholeWrapper">
      <div className="studygroupWrapper">
        <div className="createTitle">
          <label className="textLabel">Kreiraj Study Group!</label>
        </div>
        <div className="smallInputsWrapper">
          <div className="inputWrapper">
            <div className="inputs">
              <label className="inputLabel">Naziv</label>
            </div>
            <div className="inputs">
              <input
                type="text"
                name="name"
                value={groupInfoForm.name}
                onChange={onChange}
              ></input>
            </div>
          </div>
          <div className="inputWrapper">
            <div className="inputs">
              <label className="inputLabel">Datum i vrijeme</label>
            </div>
            <div className="inputs">
              <DatePicker
                selected={selectedDate}
                onChange={handleDateTimeSelect}
                showTimeSelect
                dateFormat="Pp"
                timeIntervals={15}
                placeholderText="Odaberite datum i vrijeme"
                minDate={new Date()}
              />
            </div>
          </div>
          <div className="inputWrapper">
            <div className="inputs">
              <label className="inputLabel">Maksimalan broj članova</label>
            </div>
            <div className="inputsButtons">
              <button
                className="numButton"
                onClick={() => changeParticipants(0)}
              >
                <i className="fa-solid fa-circle-minus"></i>
              </button>
              <label className="numLabel">{maxNum}</label>
              <button
                className="numButton"
                onClick={() => changeParticipants(1)}
              >
                <i className="fa-solid fa-circle-plus"></i>
              </button>
            </div>
          </div>
        </div>
        <div className="descriptionInput">
          <div className="decriptionText">
            <label className="inputLabel">Opis</label>
          </div>
          <textarea
            className="groupDescrition"
            type="text"
            name="description"
            value={groupInfoForm.description}
            onChange={onChange}
          ></textarea>
        </div>
        <div className="inputWrapper">
          <div className="inputs">
            <label className="inputLabel">Lokacija</label>
          </div>
          <div className="mapsWrapper">
            <LoadScriptNext
              googleMapsApiKey={import.meta.env.VITE_GOOGLE_MAPS_API}
              libraries={librariesHardcode}
            >
              <StandaloneSearchBox
                onLoad={(ref) => (searchBoxRef.current = ref)}
                onPlacesChanged={handlePlaceSelect}
              >
                <input
                  className="searchBar"
                  placeholder="Upišite lokaciju"
                  type="text"
                />
              </StandaloneSearchBox>
              <GoogleMap
                onLoad={(map) => (mapRef.current = map)}
                center={location}
                zoom={15}
                options={{
                  mapId: import.meta.env.VITE_GOOGLE_MAPS_MAPID,
                  streetViewControl: false,
                  mapTypeControl: false,
                  draggableCursor: true
                }}
                mapContainerStyle={{ width: '100%', height: '100%' }}
              ></GoogleMap>
            </LoadScriptNext>
          </div>
        </div>
        <div className="validationMessage">
          <label>{validationMessage}</label>
        </div>
        <div className="createGroupButton">
          <button onClick={onClose}>Prekini</button>
          <button onClick={onSubmit}>Podnesi</button>
        </div>
      </div>
    </div>
  );
}
CreateStudyGroupForm.propTypes = {
  onClose: PropTypes.func.isRequired
};

export default CreateStudyGroupForm;
