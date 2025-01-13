/* global google */

import { useEffect } from 'react';
import { useGoogleMap } from '@react-google-maps/api';
import PropTypes from 'prop-types';

function CustomAdvancedMarker({ lat, lng }) {
  const map = useGoogleMap();
  useEffect(() => {
    if (!map) return;

    const location = {
      lat: parseFloat(lat),
      lng: parseFloat(lng)
    };
    const markerElement = new google.maps.marker.AdvancedMarkerElement({
      map,
      position: location
    });
    return () => {
      markerElement.map = null;
    };
  }, [map, lat, lng]);

  return null;
}

CustomAdvancedMarker.propTypes = {
  lat: PropTypes.string.isRequired,
  lng: PropTypes.string.isRequired
};

export default CustomAdvancedMarker;
