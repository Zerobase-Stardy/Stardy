import React from "react";
import { useForm } from "react-hook-form";

import styled from "styled-components";

export default function AdminLogin() {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm();
  const onSubmit = (data) => console.log(data);

  return (
    <Main>
      <Wrap>
        <LoginForm onSubmit={handleSubmit(onSubmit)}>
          <h1>Admin Signup</h1>
          <label>아이디</label>
          <input {...register("adminId")} type="text" placeholder="ID" />
          <label>비밀번호</label>
          <input
            type="password"
            placeholder="Password"
            autoComplete="off"
            {...register("password", { required: true })}
          />
          <input type="submit" value="제출" className="submit" />
        </LoginForm>
      </Wrap>
    </Main>
  );
}

const Main = styled.main`
  width: 100%;
  padding-top: 70px;
  background-color: #fbfbfb;
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
  }

  .submit:hover {
    background-color: black;
    color: white;
  }
`;
