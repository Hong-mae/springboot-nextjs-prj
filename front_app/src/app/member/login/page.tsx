"use client";

import React, { useState } from "react";

const Login = () => {
  const [user, setUser] = useState({ username: "", password: "" });

  const handleSubmit = async (e) => {
    e.preventDefault();

    console.log("http://localhost:8090/api/v1/members/login");
    const url = "http://localhost:8090/api/v1/members/login";

    const resp = await fetch(url, {
      method: "POST",
      credentials: "include", // 핵심 변경점
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(user),
    });

    if (resp.ok) {
      alert("ok");
    } else {
      alert("fail");
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setUser({ ...user, [name]: value });

    console.log({ ...user, [name]: value });
  };

  const handleLogout = async () => {
    const resp = await fetch("http://localhost:8090/api/v1/members/logout", {
      method: "POST",
      credentials: "include",
    });

    if (resp.ok) alert("ok");
    else alert("fail");
  };
  return (
    <>
      <h1>로그인</h1>
      <form onSubmit={handleSubmit}>
        <input type="text" name="username" onChange={handleChange} />
        <input type="password" name="password" onChange={handleChange} />
        <button type="submit">로그인</button>
      </form>
      <button onClick={handleLogout}>로그아웃</button>
    </>
  );
};

export default Login;
