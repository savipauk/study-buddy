import { useState } from 'react';
import { serverFetch } from '../hooks/serverUtils';
import '../styles/ReportForm.css';
import PropTypes from 'prop-types';

function ReportForm({ onClose, userEmail, reportedUserEmail }) {
  const [validationMessage, setValidationMessage] = useState('');
  const [reportForm, setReportForm] = useState({ reason: '' });

  function onChange(event) {
    const { name, value } = event.target;
    setReportForm((oldForm) => ({
      ...oldForm,
      [name]: value
    }));
  }

  const isValid = () => {
    if (!reportForm.reason.trim()) {
      setValidationMessage('Razlog prijave je obavezan');
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
      await submitReportForm();
    }
  }

  async function submitReportForm() {
    const data = {
      reporterEmail: userEmail,
      reportedEmail: reportedUserEmail,
      reason: reportForm.reason
    };
    const endpoint = '/admin/submit-report';

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
        alert('Uspješno ste prijavili korisnika');
        onClose();
      } else {
        alert(
          `Prijava nije zaprimljena. 
      Status pogreške: ${response.status} - ${response.statusText}`
        );
      }
    } catch (error) {
      alert(`Pogreška: ${error.message}`);
    }
  }

  return (
    <div className="wholeWrapper">
      <div className="reportWrapper">
        <div className="reportTitle">
          <label className="textLabel">Prijavite korisnika</label>
        </div>
        <form onSubmit={handleSubmit}>
          <div className="smallInputsWrapper">
            <label className="reasonLabel">Razlog prijave</label>
            <textarea
              className="reason"
              type="text"
              name="reason"
              value={reportForm.reason}
              onChange={onChange}
            />
          </div>
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

ReportForm.propTypes = {
  onClose: PropTypes.func.isRequired,
  userEmail: PropTypes.string.isRequired,
  reportedUserEmail: PropTypes.string.isRequired
};

export default ReportForm;
