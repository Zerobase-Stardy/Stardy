import React from "react";
import styled from "styled-components";
import { FcGoogle } from "react-icons/fc";

export default function KaKaoButton() {
  return (
    <GoogleWrap>
      <a href="https://dokuny.blog/oauth2/authorization/google">
        <GoogleBtn>
          <FcGoogle />
          <p>구글로 로그인</p>
        </GoogleBtn>
      </a>
    </GoogleWrap>
  );
}

const GoogleWrap = styled.div`
  width: 100%;
`;

const GoogleBtn = styled.div`
  margin: 0 auto;
  width: 300px;
  height: 45px;
  padding: 10px;
  border-radius: 8px;
  background-color: white;
  cursor: pointer;
  font-family: Nanum;
  font-size: 14px;

  @media screen and (max-width: 440px) {
    width: 250px;
    height: 37.5px;
  }
  &:hover {
    background-color: lightgray;
  }

  svg {
    float: left;
    width: 24px;
    height: 24px;

    @media screen and (max-width: 440px) {
      width: 18px;
      height: 18px;
    }
  }

  p {
    line-height: 25px;
    color: black;

    @media screen and (max-width: 440px) {
      line-height: 16px;
      font-size: 12px;
    }
  }
`;
