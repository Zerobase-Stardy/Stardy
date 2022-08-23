import React from 'react';
import styled from "styled-components";
import kakaoimg from '../images/kakao.png'

export default function KaKaoButton() {

  return (
    <KaKaoWrap>
    <KaKaoBtn href="http://52.78.131.208:8080/oauth2/authorization/kakao">
      <Img src={kakaoimg} alt="kakao-login"/>
    </KaKaoBtn>
    </KaKaoWrap>
  );
}


const KaKaoWrap = styled.div`
width:100%
`

const KaKaoBtn = styled.a``;
const Img = styled.img``

