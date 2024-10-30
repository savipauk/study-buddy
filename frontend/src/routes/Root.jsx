// import reactLogo from '../assets/react.svg'
// import viteLogo from '/vite.svg'
// import '../styles/Root.css'
// import Login from '../components/Login'

// function Root() {
//   const FetchData = async () => {
//     const token = localStorage.getItem("access_token");
//     if (!token) {
//       console.log("Not logged in")
//     }

//     const url = "http://localhost:8080/example";

//     const res = await fetch(url, {
//       method: 'GET',
//       headers: {
//         'Content-Type': 'application/json',
//         'Authorization': `Bearer ${token}`,
//       },
//     });

//     if (res.ok) {
//       const json = await res.json()
//       console.log(json)
//     }

//     // try {
//     //   const response = await fetch(url, {
//     //     // method: "GET",
//     //     // headers: {
//     //     //   "Authorization": `Bearer ${token}`,
//     //     // },
//     //   });
//     //
//     //   // if (response.status == 401) {
//     //   //   console.log("loginaj se")
//     //   //   // redirect na http://localhost:8080/login/oauth2/code/github
//     //   //   return;
//     //   // }
//     //
//     //   if (!response.ok) {
//     //     throw new Error(`Response status: ${response.status}`);
//     //   }
//     //
//     //   const json = await response.json();
//     //   console.log(json);
//     // } catch (error) {
//     //   console.error("errorcina");
//     // }
//   };
import { useState } from "react";
import "../styles/Root.css";
import { useNavigate } from "react-router-dom";

function Root() {

        return (
                <div className="welcomeWrapper">
                        <div className="title">
                                <h1 className="text">STUDY BUDDY</h1>
                        </div>
                        <div className="description">
                                <p>Study smarter, together</p>
                        </div>
                        <div className="signUpButtonDiv">
                                <SignUpButton />
                        </div>
                </div>
        );
}

function SignUpButton() {
        const navigate = useNavigate();

        const handleClick = () => {
                navigate("/users/login");
        };
        return (
                <button className="signUpButton" onClick={handleClick}>
                        SIGN UP
                </button>
        );
}

export default Root;
