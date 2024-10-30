import reactLogo from '../assets/react.svg'
import viteLogo from '/vite.svg'
import '../styles/Root.css'
import Login from '../components/Login'

function Root() {
  const FetchData = async () => {
    const token = localStorage.getItem("access_token");
    if (!token) {
      console.log("Not logged in")
    }

    const url = "http://localhost:8080/example";

    const res = await fetch(url, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      },
    });

    if (res.ok) {
      const json = await res.json()
      console.log(json)
    }

    // try {
    //   const response = await fetch(url, {
    //     // method: "GET",
    //     // headers: {
    //     //   "Authorization": `Bearer ${token}`,
    //     // },
    //   });
    //
    //   // if (response.status == 401) {
    //   //   console.log("loginaj se")
    //   //   // redirect na http://localhost:8080/login/oauth2/code/github
    //   //   return;
    //   // }
    //
    //   if (!response.ok) {
    //     throw new Error(`Response status: ${response.status}`);
    //   }
    //
    //   const json = await response.json();
    //   console.log(json);
    // } catch (error) {
    //   console.error("errorcina");
    // }
  };

  return (
    <>
      <div>
        <a href="https://vitejs.dev" target="_blank">
          <img src={viteLogo} className="logo" alt="Vite logo" />
        </a>
        <a href="https://react.dev" target="_blank">
          <img src={reactLogo} className="logo react" alt="React logo" />
        </a>
      </div>
      <h1>Study Buddy</h1>
      <Login />
      <div>
        <button onClick={FetchData}>
          Example Endpoint
        </button>
      </div>
      <p>
        Using Vite and React
      </p>
    </>
  )
}

export default Root
