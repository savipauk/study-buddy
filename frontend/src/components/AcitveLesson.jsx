import { useState } from 'react';
import '../styles/ActiveGroups.css';
import LessonInfo from './LessonInfo';
import PropTypes from 'prop-types';

function ActiveLesson({ lesson, joinedGroups }) {
  const [showInfo, setShowInfo] = useState(false);

  return (
    <>
      {!showInfo && (
        <div className="activeGroupsWrapper">
          <div className="rightWrapper">
            <div className="groupType">
              <label>{'Instrukcije'}</label>
            </div>
            <div className="wrapper">
              <div className="basicInfoWrapper">
                <div className="infoSubject">
                  <label>{lesson.subject}</label>
                </div>
                <div className="info">
                  <label>{lesson.date}</label>
                </div>
                <div className="info">
                  <label>{lesson.username}</label>
                </div>
              </div>
            </div>
          </div>
          <div className="leftWrapper">
            <div className="joinGroup">
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
        <LessonInfo
          lesson={lesson}
          onClose={() => setShowInfo(false)}
          joinedGroups={joinedGroups}
        />
      )}
    </>
  );
}

ActiveLesson.propTypes = {
  lesson: PropTypes.shape({
    subject: PropTypes.string,
    date: PropTypes.string,
    username: PropTypes.string
  }).isRequired,
  joinedGroups: PropTypes.array.isRequired
};
export default ActiveLesson;
