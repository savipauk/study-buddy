import React, { useState } from 'react';
import { serverFetch } from '../hooks/serverUtils';
import '../styles/ReviewForm.css';
import PropTypes from 'prop-types';

function ReviewForm({ onClose, studentUsername, professorUsername }) {
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
      studentUsername: studentUsername,
      professorUsername: professorUsername,
      rating: rating,
      comment: comment
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
        alert('Uspješno ste ostavili recenziju');
        onClose();
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
    <div className="review-form">
      <h2>Dodaj recenziju!</h2>
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
      <form onSubmit={handleSubmit}>
        <label htmlFor="comment">Opis</label>
        <textarea
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
  );
}

export default ReviewForm;
