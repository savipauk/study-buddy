import { useState } from 'react';
import '../styles/SearchBar.css';
import PropTypes from 'prop-types';

function SearchBarFilter({ onFilterChange }) {
  const [dropdown, setDropdown] = useState(false);
  const [searchBar, setSearchBar] = useState('');
  const [currentFilter, setCurrentFilter] = useState('');
  const [currentName, setCurrentName] = useState('Filter');

  const applyFilter = (filterType) => {
    setDropdown(false);
    setCurrentFilter(filterType);
    onFilterChange(filterType);
  };

  const handleSearch = (event) => {
    setSearchBar(event.target.value);
  };

  return (
    <div className="filterOptions">
      <div className="filterInputsSearchbar">
        <div className="searchBarWrapper">
          <i className="fa-solid fa-magnifying-glass"></i>
          <input
            className="filterBar"
            type="text"
            placeholder="Search..."
            value={searchBar}
            onChange={handleSearch}
          />
        </div>
      </div>
      <div className="filterInputs">
        <button className="filterButton" onClick={() => setDropdown(!dropdown)}>
          {currentName} {currentFilter}
          <i className="fa-solid fa-sort-down"></i>
        </button>
        {dropdown && (
          <div className="dropdownMenu">
            <button
              onClick={() => {
                applyFilter('Najblizi');
                setCurrentName('Datum:');
              }}
            >
              Najblizi
            </button>
            <button
              onClick={() => {
                applyFilter('Najdalji');
                setCurrentName('Datum:');
              }}
            >
              Najdalji
            </button>
            <button
              onClick={() => {
                applyFilter('Instrukcije');
                setCurrentName('Samo:');
              }}
            >
              Instrukcije
            </button>
            <button
              onClick={() => {
                applyFilter('StudyGrupe');
                setCurrentName('Samo:');
              }}
            >
              StudyGrupe
            </button>
          </div>
        )}
      </div>
    </div>
  );
}
SearchBarFilter.propTypes = {
  onFilterChange: PropTypes.func.isRequired
};

export default SearchBarFilter;
