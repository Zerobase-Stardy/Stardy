import React, { useEffect, useRef } from "react";
import styled from "styled-components";
import { RiCloseLine } from "react-icons/ri";
import GoogleButton from "./GoogleButton";
import KaKaoButton from "./KaKaoButton";

export default function Login(props) {
  const loginRef = useRef();
  const { toggle, openModal } = props;

  function handleClickOutside(event) {
    if (openModal && !loginRef.current.contains(event.target)) {
      toggle();
    }
  }
  useEffect(() => {
    document.addEventListener("mousedown", handleClickOutside);
    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  });

  return (
    <Modal>
      <LoginArea ref={loginRef}>
        <CloseBtn>
          <RiCloseLine onClick={toggle} />
        </CloseBtn>
        <span>로그인</span>
        <ButtonArea>
          <GoogleButton />
          <KaKaoButton />
        </ButtonArea>
        <AdminZone></AdminZone>
      </LoginArea>
    </Modal>
  );
}

const Modal = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  width: 100%;
  height: 100vh;
  z-index: 100;
  background-color: rgba(0, 0, 0, 0.85);
`;

const LoginArea = styled.div`
  position: fixed;
  top: 45%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 400px;
  height: 400px;
  padding: 30px;
  text-align: center;
  background-color: black;
  border: 1px solid rgba(0, 204, 0, 0.6);
  border: 1px solid #800000;

  span {
    letter-spacing: 3px;
    font-size: 24px;
    color: #00cc00;
  }
`;

const CloseBtn = styled.div`
  width: 100%;
  text-align: right;
  font-size: 24px;
  color: white;
  svg {
    cursor: pointer;

    &:hover {
      color: #00cc00;
      border: 2px solid #00cc00;
      border-radius: 50px;
    }
  }
`;

const ButtonArea = styled.div`
  width: 100%;
  display: grid;
  margin-top: 50px;
  gap: 20px;
`;

const AdminZone = styled.div`
  margin-top: 30px;
  font-size: 13px;
  width: 100%;
  padding-left: 20px;
  display: flex;
  gap: 10px;
`;
