import React, { useEffect, useState } from "react";
import styled from "styled-components";
import axios from "axios";
import kakaoimg from "../images/kakao.png";

export default function KaKaoButton() {
  return (
    <KaKaoWrap>
      <KaKaoBtn href="http://52.78.131.208:8080/oauth2/authorization/kakao">
        <button>쿠키</button>
        <Img src={kakaoimg} alt="kakao-login" />
      </KaKaoBtn>
    </KaKaoWrap>
  );
}

const KaKaoWrap = styled.div`
  width: 100%;
`;
const KaKaoBtn = styled.a``;
const Img = styled.img``;
