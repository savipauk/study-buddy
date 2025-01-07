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
import PropTypes from 'prop-types';
import { serverFetch } from '../hooks/serverUtils';

const librariesHardcode = ['places', 'marker'];

function CreateInstructionForm({ onClose }) {
  const mapLocation = {
    //Zagreb
    lat: 45.815,
    lng: 15.9819
  };
  const [selectedDate, setSelectedDate] = useState(null);
  const [date, setDate] = useState(null);
  const [time, setTime] = useState(null);
  const [locationName, setLocationName] = useState(null);
  const [location, setLocation] = useState(mapLocation);
  const [maxNum, setMaxNum] = useState(0);
  const [minNum, setMinNum] = useState(0);
  const [validationMessage, setValidationMessage] = useState('');
  const mapRef = useRef(null);
  const searchBoxRef = useRef(null);
  const markerRef = useRef(null);
  const [instructionInfoForm, setInstructionInfoForm] = useState({
    subject: '',
    price: '',
    duration: '',
    type: ''
  });

  function onChange(event) {
    const { name, value } = event.target;
    setInstructionInfoForm((oldForm) => ({ ...oldForm, [name]: value }));
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

  const changeParticipants = (id, type) => {
    if (type === 0) {
      if (id === 0 && maxNum > 0) {
        setMaxNum(maxNum - 1);
      } else if (id === 1) {
        setMaxNum(maxNum + 1);
      }
    } else if (type === 1) {
      if (id === 0 && minNum > 0) {
        setMinNum(minNum - 1);
      } else if (id === 1 && minNum < maxNum) {
        setMinNum(minNum + 1);
      }
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
    if (!instructionInfoForm.subject.trim()) {
      setValidationMessage('Predmet je obavezan');
      return false;
    }
    if (!instructionInfoForm.duration.trim()) {
      setValidationMessage('Trajanje je obavezan');
      return false;
    }
    if (!instructionInfoForm.price.trim()) {
      setValidationMessage('CIjena je obavezan');
      return false;
    }
    if (!instructionInfoForm.type.trim()) {
      setValidationMessage('Tip instrukcija je obavezan');
      return false;
    }
    if (!selectedDate) {
      setValidationMessage('Molimo odaberite datum i vrijeme');
      return false;
    }
    if (maxNum <= 0) {
      setValidationMessage('Maksimalan broj clanova mora biti veći od 0');
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
      await createNewLesson();
      onClose();
    }
  }

  async function createNewLesson() {
    const email = localStorage.getItem('user_email');
    const endpoint = '/lesson/create';
    const data = {
      email: email,
      subject: instructionInfoForm.subject,
      duration: instructionInfoForm.duration,
      maxMembers: maxNum,
      minMembers: minNum,
      xCoordinate: location.lat,
      yCoordinate: location.lng,
      type: instructionInfoForm.type,
      price: instructionInfoForm.price,
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
          <label className="textLabel">Objavi Instrukcije!</label>
        </div>
        <div className="allInputsWrapper">
          <div className="partialInfoWrapper">
            <div className="inputWrapper">
              <div className="inputs">
                <label className="inputLabel">Predmet</label>
              </div>
              <div className="inputs">
                <input
                  type="text"
                  name="subject"
                  value={instructionInfoForm.subject}
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
                <label className="inputLabel">Cijena</label>
              </div>
              <div className="inputs">
                <input
                  type="text"
                  name="price"
                  value={instructionInfoForm.price}
                  onChange={onChange}
                ></input>
              </div>
            </div>
          </div>
          <div className="partialInfoWrapper">
            <div className="inputWrapper">
              <div className="inputs">
                <label className="inputLabel">Trajanje</label>
              </div>
              <div className="inputs">
                <input
                  type="text"
                  name="duration"
                  value={instructionInfoForm.duration}
                  onChange={onChange}
                ></input>
              </div>
            </div>
            <div className="inputWrapper">
              <div className="inputs">
                <label className="inputLabel">Maksimalan broj clanova</label>
              </div>
              <div className="inputsButtons">
                <button
                  className="numButton"
                  onClick={() => changeParticipants(0, 0)}
                >
                  <i className="fa-solid fa-circle-minus"></i>
                </button>
                <label className="numLabel">{maxNum}</label>
                <button
                  className="numButton"
                  onClick={() => changeParticipants(1, 0)}
                >
                  <i className="fa-solid fa-circle-plus"></i>
                </button>
              </div>
            </div>
            <div className="inputWrapper">
              <div className="inputs">
                <label className="inputLabel">Minimalan broj clanova</label>
              </div>
              <div className="inputsButtons">
                <button
                  className="numButton"
                  onClick={() => changeParticipants(0, 1)}
                >
                  <i className="fa-solid fa-circle-minus"></i>
                </button>
                <label className="numLabel">{minNum}</label>
                <button
                  className="numButton"
                  onClick={() => changeParticipants(1, 1)}
                >
                  <i className="fa-solid fa-circle-plus"></i>
                </button>
              </div>
            </div>
          </div>
        </div>
        <div className="inputWrapper">
          <div className="inputs">
            <label className="inputLabel">Tip instrukcija</label>
          </div>
          <div className="typeWrapper">
            <input
              className="typeRadioButton"
              type="radio"
              name="type"
              value={'OneOnOne'}
              id="typeOne"
              checked={instructionInfoForm.type === 'OneOnOne'}
              onChange={onChange}
            ></input>
            <label htmlFor="typeOne" className="toggleOptionType">
              Jedan na jedan
            </label>
            <input
              className="typeRadioButton"
              type="radio"
              name="type"
              value={'Massive'}
              id="typeMassive"
              checked={instructionInfoForm.type === 'Massive'}
              onChange={onChange}
            ></input>
            <label htmlFor="typeMassive" className="toggleOptionType">
              Masivne
            </label>
          </div>
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
CreateInstructionForm.propTypes = {
  onClose: PropTypes.func.isRequired
};

export default CreateInstructionForm;
