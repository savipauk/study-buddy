import { useState, useEffect } from 'react';
import { serverFetch } from '../hooks/serverUtils';
import ReportedUser from '../components/ReportedUser';
import '../styles/Admin.css';

function AdminPage() {
  const [allReports, setAllReports] = useState([]);
  const [openReports, setOpenReports] = useState([]);
  const [inProgressReports, setInProgressReports] = useState([]);
  const [closedReports, setClosedReports] = useState([]);
  const [rejectedReports, setRejectReports] = useState([]);
  const [selectedOption, setSelectedOption] = useState('allReports');
  const [refreshReports, setRefreshReports] = useState(false);

  useEffect(() => {
    const fetchData = async () => {
      try {
        await getReports('allReports');
        await getReports('allStatusReports/OPEN');
        await getReports('allStatusReports/IN_PROGRESS');
        await getReports('allStatusReports/CLOSED');
        await getReports('allStatusReports/REJECTED');
      } catch (error) {
        console.error('Error fetching groups:', error);
      }
    };

    fetchData();
  }, [refreshReports]);

  async function getReports(type) {
    const endpoint = `/admin/${type}`;
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
        if (type === 'allReports') {
          setAllReports(data);
        } else if (type === 'allStatusReports/OPEN') {
          setOpenReports(data);
        } else if (type === 'allStatusReports/IN_PROGRESS') {
          setInProgressReports(data);
        } else if (type === 'allStatusReports/CLOSED') {
          setClosedReports(data);
        } else if (type === 'allStatusReports/REJECTED') {
          setRejectReports(data);
        }
      } else {
        console.log('response error');
      }
    } catch (error) {
      console.log(error);
    }
  }

  return (
    <div className="adminWrapper">
      <h1>Admin</h1>
      <div className="toggleSwitch">
        <button
          onClick={() => setSelectedOption('allReports')}
          className={selectedOption === 'allReports' ? 'active' : ''}
        >
          Svi reportovi
        </button>
        <button
          onClick={() => setSelectedOption('openReports')}
          className={selectedOption === 'openReports' ? 'active' : ''}
        >
          Otvoreni reportovi
        </button>
        <button
          onClick={() => setSelectedOption('inProgressReports')}
          className={selectedOption === 'inProgressReports' ? 'active' : ''}
        >
          U tijeku
        </button>
        <button
          onClick={() => setSelectedOption('closedReports')}
          className={selectedOption === 'closedReports' ? 'active' : ''}
        >
          Zatvoreni
        </button>
      </div>
      <div className="allReportsWrapper">
        {selectedOption === 'allReports' && (
          <div className="reportsWrapper">
            <label>Svi reportovi</label>
            <div className="reports">
              {allReports.map((report, index) => (
                <ReportedUser
                  reportedUser={report}
                  key={index}
                  onButtonClick={() => {
                    setRefreshReports((prev) => !prev);
                  }}
                />
              ))}
            </div>
          </div>
        )}
        {selectedOption === 'openReports' && (
          <div className="reportsWrapper">
            <label>Otvoreni reportovi</label>
            <div className="reports">
              {openReports.map((openReport, index) => (
                <ReportedUser
                  reportedUser={openReport}
                  key={index}
                  onButtonClick={() => {
                    setRefreshReports(!refreshReports);
                  }}
                />
              ))}
            </div>
          </div>
        )}
        {selectedOption === 'inProgressReports' && (
          <div className="reportsWrapper">
            <label>U tijeku</label>
            <div className="reports">
              {inProgressReports.map((inProgressReport, index) => (
                <ReportedUser
                  reportedUser={inProgressReport}
                  key={index}
                  onButtonClick={() => {
                    setRefreshReports(!refreshReports);
                  }}
                />
              ))}
            </div>
          </div>
        )}
        {selectedOption === 'closedReports' && (
          <div className="reportsWrapper">
            <label>Zatvoreni</label>
            <div className="reports">
              {[...closedReports, ...rejectedReports].map(
                (closedReport, index) => (
                  <ReportedUser
                    reportedUser={closedReport}
                    key={index}
                    onButtonClick={() => {
                      setRefreshReports(!refreshReports);
                    }}
                  />
                )
              )}
            </div>
          </div>
        )}
      </div>
    </div>
  );
}

export default AdminPage;
