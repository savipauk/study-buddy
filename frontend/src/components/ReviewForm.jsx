import { useState } from 'react';
import { serverFetch } from '../hooks/serverUtils';
import '../styles/ReviewForm.css';
import PropTypes from 'prop-types';

function ReviewForm({ onClose, studentEmail, professorUsername }) {
  const [validationMessage, setValidationMessage] = useState('');
  const [rating, setRating] = useState(0);
  const [comment, setComment] = useState('');

  const handleRatingClick = (value) => {
    setRating(value);
  };

  const isValid = () => {
    if (!rating) {
      setValidationMessage('Ocijena je obavezna ');
      return false;
    }
    setValidationMessage('');
    return true;
  };

  async function handleSubmit(event) {
    event.preventDefault();
    if (!isValid()) {
      return;
    } else {
      await submitReviewForm();
    }
  }

  async function submitReviewForm() {
    const data = {
      studentEmail: studentEmail,
      professorUsername: professorUsername,
      comment: comment,
      rating: rating
    };

    const endpoint = '/reviews/create';

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
        console.log('Odgovor servera:', response);

        alert('Uspješno ste ostavili recenziju');
        onClose();
        console.log(data);
      } else {
        alert(
          `Recenzija nije zaprimljena. 
      Status pogreške: ${response.status} - ${response.statusText}`
        );
      }
    } catch (error) {
      alert(`Pogreška: ${error.message}`);
    }
  }

  return (
    <div className="whole-page">
      <div className="review-form">
        <h2>Dodajte recenziju!</h2>
        <div className="stars">
          {[1, 2, 3, 4, 5].map((star) => (
            <span
              key={star}
              className={star <= rating ? 'star filled' : 'star'}
              onClick={() => handleRatingClick(star)}
            >
              ★
            </span>
          ))}
        </div>
        <form className="review-input" onSubmit={handleSubmit}>
          <label className="description-label" htmlFor="comment">
            Opis
          </label>
          <textarea
            className="comment-section"
            id="comment"
            value={comment}
            onChange={(e) => setComment(e.target.value)}
          ></textarea>
          {validationMessage && (
            <div className="validationMessage">
              <label>{validationMessage}</label>
            </div>
          )}
          <div className="submitReportButton">
            <button type="button" onClick={onClose}>
              Prekini
            </button>
            <button type="submit">Podnesi</button>
          </div>
        </form>
      </div>
    </div>
  );
}

ReviewForm.propTypes = {
  onClose: PropTypes.func.isRequired,
  studentEmail: PropTypes.string.isRequired,
  professorUsername: PropTypes.string.isRequired
};

export default ReviewForm;
