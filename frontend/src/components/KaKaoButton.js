import React, { useEffect, useState } from "react";
import styled from "styled-components";
import kakaoimg from "../images/kakao.png";

export default function KaKaoButton() {
  return (
    <KaKaoWrap>
      <KaKaoBtn href={process.env.REACT_APP_KAKAO_AUTH_URL}>
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
