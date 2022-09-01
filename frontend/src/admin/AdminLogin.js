import axios from "axios";
import React, { useState } from "react";
import styled from "styled-components";
import cookies from "react-cookies";
export default function AdminLogin() {
  const [inputs, setInputs] = useState([]);

  function changeHandler(e) {
    setInputs({
      ...inputs,
      [e.target.name]: e.target.value,
    });
  }

  function submitHandler(e) {
    e.preventDefault();
    axios
      .post("https://www.dokuny.blog/admin/login", {
        adminId: inputs.adminId,
        password: inputs.password,
      })
      .then((res) => {
        if (res.status === 200) {
          cookies.save("accessToken", res.data.data.accessToken, {
            path: "/",
          });
          document.location.href = "/admin";
        }
        console.log(res.status, res.data.data.accessToken);
      })
      .catch((res) => {
        if (res.response.status === 400) {
          alert("등록 되지 않은 계정입니다.");
        }
      });
  }
  return (
    <Main>
      <Wrap>
        <LoginForm onSubmit={submitHandler}>
          <h1>Admin Login</h1>
          <label>아이디</label>
          <input
            type="text"
            placeholder="adminId"
            name="adminId"
            onChange={changeHandler}
          />
          <label>비밀번호</label>
          <input
            type="password"
            placeholder="Password"
            name="password"
            autoComplete="off"
            onChange={changeHandler}
          />
          <input type="submit" value="로그인" className="submit" />
        </LoginForm>
      </Wrap>
    </Main>
  );
}

const Main = styled.div`
  width: 100%;
  padding-top: 70px;
`;

const Wrap = styled.div`
  width: 60%;
  height: 100px;
  margin: 0 auto;

  h1 {
    text-align: center;
    font-size: 40px;
    font-weight: bold;
    margin-bottom: 30px;
  }
`;

const LoginForm = styled.form`
  width: 400px;
  padding: 50px;
  margin: 0 auto;
  display: grid;
  gap: 20px;
  border: 1px solid black;
  input {
    height: 40px;
    padding: 5px;
    border: 0;
    border: 1px solid black;
  }

  .submit:hover {
    background-color: black;
    color: white;
  }
`;
