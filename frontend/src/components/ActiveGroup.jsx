import { useState } from 'react';
import '../styles/ActiveGroups.css';
import StudyGroupInfo from './StudyGroupInfo';
import PropTypes from 'prop-types';

function ActiveGroup({ group, joinedGroups, onLeave }) {
  const [showInfo, setShowInfo] = useState(false);

  return (
    <>
      {!showInfo && (
        <div className='activeGroupsWrapper'>
          <div className='rightWrapper'>
            <div className='groupType'>
              <label>{'StudyGroup'}</label>
            </div>
            <div className='wrapper'>
              <div className='basicInfoWrapper'>
                <div className='infoSubject'>
                  <label>{group.groupName}</label>
                </div>
                <div className='info'>
                  <label>{group.date}</label>
                </div>
                <div className='info'>
                  <label>{group.username}</label>
                </div>
              </div>
            </div>
          </div>
          <div className='leftWrapper'>
            <div className='joinGroup'>
              <button
                onClick={() => {
                  setShowInfo(true);
                }}
              >
                Pogledaj Info
              </button>
            </div>
          </div>
        </div>
      )}
      {showInfo && (
        <StudyGroupInfo
          group={group}
          onClose={() => setShowInfo(false)}
          joinedGroups={joinedGroups}
          onLeave={onLeave}
        />
      )}
    </>
  );
}

ActiveGroup.propTypes = {
  group: PropTypes.shape({
    username: PropTypes.string,
    date: PropTypes.string,
    groupName: PropTypes.string
  }).isRequired,
  joinedGroups: PropTypes.array.isRequired,
  onLeave: PropTypes.func.isRequired
};
export default ActiveGroup;
