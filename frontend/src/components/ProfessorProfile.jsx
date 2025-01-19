import { useEffect, useState } from 'react';
import { serverFetch } from '../hooks/serverUtils';
import ReportForm from './ReportForm';
import ReviewForm from './ReviewForm';
import '../styles/StudentProfessorProfile.css';
import PropTypes from 'prop-types';
import { useNavigate } from 'react-router-dom';

function ProfessorProfile({ onClose, username }) {
  const role = localStorage.getItem('role');
  const [userInfoForm, setUserInfoForm] = useState({
    FirstName: '',
    LastName: '',
    Bio: '',
    Email: ''
  });

  const [profilePictureUrl, setProfilePictureUrl] = useState('');
  const [averageRating, setAverageRating] = useState(0);
  const [reviews, setReviews] = useState([]);
  const [showReviews, setShowReviews] = useState(false);
  const [showReportWindow, setShowReportWindow] = useState(false);
  const [showReviewWindow, setShowReviewWindow] = useState(false);
  const navigate = useNavigate();

  const handleCloseWindow = () => {
    setShowReportWindow(false);
    setShowReviewWindow(false);
    setShowReviews(false);
  };

  const handleReportUser = () => setShowReportWindow(true);
  const handleReviewUser = () => setShowReviewWindow(true);
  const handleShowReviews = () => setShowReviews(true);

  const getProfessorProfile = async (username) => {
    const endpoint = `/users/profileByUsername/${username}`;
    const options = {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json'
      }
    };

    try {
      const response = await serverFetch(endpoint, options);
      if (response.ok) {
        const data = await response.json();
        setUserInfoForm({
          FirstName: data.firstName || '-',
          LastName: data.lastName || '-',
          Bio: data.description || '-',
          Email: data.email || ''
        });
      } else {
        console.error('Failed to fetch user profile');
      }

      const imageResponse = await serverFetch(
        `/users/profile-picture/${username}`,
        {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json'
          }
        }
      );
      if (imageResponse.ok) {
        const blob = await imageResponse.blob();
        if (blob.size > 0) {
          setProfilePictureUrl(URL.createObjectURL(blob));
        } else {
          setProfilePictureUrl(
            'https://static.vecteezy.com/system/resources/previews/005/129/844/non_2x/profile-user-icon-isolated-on-white-background-eps10-free-vector.jpg'
          );
        }
      } else {
        console.error('Greška pri dohvaćanju profilne slike');
      }
    } catch (error) {
      console.error('Greška:', error);
    }
  };

  const getAverageRating = async (username) => {
    try {
      const response = await serverFetch(`/reviews/averageRating/${username}`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json'
        }
      });
      if (response.ok) {
        const data = await response.json();
        setAverageRating(data.averageRating || 0);
      } else {
        console.error('Failed to fetch average rating');
      }
    } catch (error) {
      console.error('Error fetching average rating:', error);
    }
  };

  const getAllReviewsForProfessor = async (username) => {
    try {
      const response = await serverFetch(`/reviews/allReviews/${username}`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json'
        }
      });
      if (response.ok) {
        const data = await response.json();
        setReviews(data);
      } else {
        console.error('Failed to fetch reviews');
      }
    } catch (error) {
      console.error('Error fetching reviews:', error);
    }
  };

  const renderStars = (rating) => {
    const stars = [];
    for (let i = 1; i <= 5; i++) {
      stars.push(
        <span key={i} className={i <= rating ? 'star filled' : 'star'}>
          ★
        </span>
      );
    }
    return stars;
  };

  useEffect(() => {
    getProfessorProfile(username);
    getAverageRating(username);
    getAllReviewsForProfessor(username);
  }, [username]);

  return (
    <div className="wholePage">
      <div className="profileContainer">
        <div className="profile-container">
          <div className="profile-header">
            <div className="profile-picture">
              <img src={profilePictureUrl} />
            </div>
            <div className="profile-info">
              <p>Ime: {userInfoForm.FirstName}</p>
              <p>Prezime: {userInfoForm.LastName}</p>
              <p>Opis: {userInfoForm.Bio}</p>
              <p>Prosječna ocjena: {renderStars(averageRating)}</p>
              <div className="showProfile">
                <button
                  className="showProfileButton"
                  onClick={() => navigate(`/users/profile/${username}`)}
                >
                  Prikaži Profil
                </button>
              </div>
            </div>
          </div>
          {showReviews && (
            <div className="reviewsModal">
              <h2>Sve recenzije</h2>
              <ul>
                {reviews.map((review, index) => (
                  <li key={index}>
                    <p>
                      <strong>Student:</strong> {review.studentUsername}
                    </p>
                    <p>
                      <strong>Komentar:</strong> {review.comment}
                    </p>
                    <p>
                      <strong>Ocjena:</strong> {review.rating}
                    </p>
                  </li>
                ))}
              </ul>
              <button onClick={handleCloseWindow}>Zatvori</button>
            </div>
          )}
          {!showReviews && (
            <div className="buttons">
              {role === 'STUDENT' && (
                <button className="review-button" onClick={handleReviewUser}>
                  Ocijenite korisnika!
                </button>
              )}
              <button className="reviews-button" onClick={handleShowReviews}>
                Prikaži sve recenzije
              </button>
              <button className="report-button" onClick={handleReportUser}>
                Prijavite korisnika!
              </button>
              <button className="close-button" onClick={onClose}>
                Zatvori
              </button>
            </div>
          )}
          {showReportWindow && (
            <ReportForm
              onClose={handleCloseWindow}
              userEmail={localStorage.getItem('user_email')}
              reportedUserEmail={userInfoForm.Email}
            />
          )}
          {showReviewWindow && (
            <ReviewForm
              onClose={handleCloseWindow}
              studentEmail={localStorage.getItem('user_email')}
              professorUsername={username}
            />
          )}
        </div>
      </div>
    </div>
  );
}

ProfessorProfile.propTypes = {
  onClose: PropTypes.func.isRequired,
  username: PropTypes.string.isRequired
};

export default ProfessorProfile;
