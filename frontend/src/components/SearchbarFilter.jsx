import { useState } from 'react';
import '../styles/SearchBar.css';
import PropTypes from 'prop-types';

function SearchBarFilter({ onFilterChange, onSearchBarEnter }) {
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
    const inputValue = event.target.value;
    if (inputValue.trim() !== '' && inputValue !== searchBar) {
      setSearchBar(inputValue);
      onSearchBarEnter(inputValue);
    } else if (inputValue === '') {
      setSearchBar('');
      onSearchBarEnter('');
    }
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
            <div className="dropdownSection">
              <label className="dropdownLabel">Datum</label>
              <button
                className="dropdownButton"
                onClick={() => {
                  applyFilter('Najblizi');
                  setCurrentName('Datum:');
                }}
              >
                Najblizi
              </button>
              <button
                className="dropdownButton"
                onClick={() => {
                  applyFilter('Najdalji');
                  setCurrentName('Datum:');
                }}
              >
                Najdalji
              </button>
            </div>
            <div className="dropdownSection">
              <label className="dropdownLabel">Tip</label>
              <button
                className="dropdownButton"
                onClick={() => {
                  applyFilter('Instrukcije');
                  setCurrentName('Tip:');
                }}
              >
                Instrukcije
              </button>
              <button
                className="dropdownButton"
                onClick={() => {
                  applyFilter('StudyGrupe');
                  setCurrentName('Tip:');
                }}
              >
                StudyGrupe
              </button>
            </div>
            <div className="dropdownSectionReset">
              <button
                className="dropdownButtonReset"
                onClick={() => {
                  applyFilter('');
                  setCurrentName('Filter');
                }}
              >
                Resetiraj
              </button>
            </div>
          </div>
        )}
      </div>
    </div>
  );
}
SearchBarFilter.propTypes = {
  onFilterChange: PropTypes.func.isRequired,
  onSearchBarEnter: PropTypes.func.isRequired
};

export default SearchBarFilter;
