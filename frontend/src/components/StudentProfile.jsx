import { useEffect, useState } from 'react';
import { getUserData } from '../hooks/serverUtils';
import ReportForm from './ReportForm';

function StudentProfile({ onClose }) {
  const [userInfoForm, setUserInfoForm] = useState({
    FirstName: '',
    LastName: '',
    Bio: '',
    studyGroups: []
  });

  const [showReportWindow, setShowReportWindow] = useState(false);

  const handleCloseWindow = () => {
    setShowReportWindow(false);
  };

  const handleReportUser = () => setShowReportWindow(true);

  const fetchUserData = async () => {
    const otherUserEmail = localStorage.getItem('other_user_email'); //provjeri ovo sa backom za ispravan dohvat podataka
    const userData = await getUserData(otherUserEmail);
    if (userData) {
      setUserInfoForm({
        FirstName: userData.firstName,
        LastName: userData.lastName, //dodati mozda email?
        Bio: userData.description,
        studyGroups: userData.studyGroups || [] // Add this
      });
    }
  };

  const loadMoreGroups = async () => {
    const moreGroups = await fetchAdditionalGroups(); // generirao chat
    setUserInfoForm((prev) => ({
      ...prev,
      studyGroups: [...prev.studyGroups, ...moreGroups]
    }));
  };

  useEffect(() => {
    fetchUserData();
  }, []);

  return (
    <>
      <div className="profile">
        <div className="profile-container">
          <div className="profile-header">
            <div className="profile-picture"></div>
            <div className="profile-info">
              <p>
                <strong>Ime</strong> {userInfoForm.FirstName}
              </p>
              <p>
                <strong>Prezime</strong> {userInfoForm.LastName}
              </p>
              <p>
                <strong>Opis</strong> {userInfoForm.Bio}
              </p>
            </div>
          </div>

          <div className="study-groups">
            <h2>Popis Study Group-a</h2>
            {userInfoForm.studyGroups.map((group, index) => (
              <div key={index} className="study-group">
                <span>{group.name}</span>
                {group.isActive ? (
                  <span className="active">Aktivno</span>
                ) : (
                  <span className="expired">Isteklo</span>
                )}
                {group.isActive && (
                  <button className="join-button">Pridruži se!</button>
                )}
              </div>
            ))}
            <button className="load-more" onClick={loadMoreGroups}>
              Učitaj još...
            </button>
          </div>

          <button className="report-button" onClick={handleReportUser}>
            Prijavite korisnika!
          </button>
          <button className="close-button" onClick={onClose}>
            Zatvori
          </button>
        </div>
        {showReportWindow && (
          <ReportForm
            onClose={handleCloseWindow}
            userEmail={localStorage.getItem('user_email')}
            reportedUserEmail={userInfoForm.Email}
          />
        )}
      </div>
    </>
  );
}

export default StudentProfile;
