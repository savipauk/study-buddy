/* global google */

import { useEffect } from 'react';
import { useGoogleMap } from '@react-google-maps/api';
import PropTypes from 'prop-types';

function CustomAdvancedMarker({ lat, lng }) {
  const map = useGoogleMap();
  const location = {
    lat: parseFloat(lat),
    lng: parseFloat(lng)
  };
  useEffect(() => {
    if (!map) return;
    const markerElement = new google.maps.marker.AdvancedMarkerElement({
      map,
      position: location
    });
    return () => {
      markerElement.map = null;
    };
  }, [map, location]);

  return null;
}

CustomAdvancedMarker.propTypes = {
  lat: PropTypes.string.isRequired,
  lng: PropTypes.string.isRequired
};

export default CustomAdvancedMarker;
