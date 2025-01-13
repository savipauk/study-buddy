import '../styles/Admin.css';
import PropTypes from 'prop-types';
import { serverFetch } from '../hooks/serverUtils';

function ReportedUser({ reportedUser, onButtonClick }) {
  async function takeAction(type, action, id) {
    const endpoint = `/${type}/${action}/${id}`;
    const methodType = action === 'delete' ? 'DELETE' : 'POST';
    const options = {
      method: methodType,
      headers: {
        'Content-Type': 'application/json'
      }
    };
    console.log(endpoint);
    try {
      const response = await serverFetch(endpoint, options);
      if (response.ok) {
        onButtonClick();
      } else {
        console.log('response error');
      }
    } catch (error) {
      console.log(error);
    }
  }

  return (
    <div className='reportedUserWrapper'>
      <div className='reportCard'>
        <label className='labelInfo'>Korisnicko ime</label>
        <label>{reportedUser.reportedUsername}</label>
      </div>
      <div className='reportCard'>
        <label className='labelInfo'>Razlog</label>
        <label>{reportedUser.reason}</label>
      </div>
      {(reportedUser.status === 'OPEN' ||
        reportedUser.status === 'IN_PROGRESS') && (
        <div className='decisionButtons'>
          <button
            onClick={async () =>
              takeAction('users', 'delete', reportedUser.reportedEmail)
            }
          >
            Ukloni racun
          </button>
          <button
            onClick={async () =>
              takeAction('admin', 'rejectedReport', reportedUser.reportId)
            }
          >
            Zanemari
          </button>
        </div>
      )}
      {(reportedUser.status === 'CLOSED' ||
        reportedUser.status === 'REJECTED') && (
        <div className='reportCard'>
          <label className='labelInfo'>Status</label>
          <label>{reportedUser.status}</label>
        </div>
      )}
    </div>
  );
}
ReportedUser.propTypes = {
  reportedUser: PropTypes.shape({
    reportedUsername: PropTypes.string,
    reason: PropTypes.string,
    status: PropTypes.string,
    reportedEmail: PropTypes.string,
    reportId: PropTypes.number
  }).isRequired,
  onButtonClick: PropTypes.func.isRequired
};
export default ReportedUser;
