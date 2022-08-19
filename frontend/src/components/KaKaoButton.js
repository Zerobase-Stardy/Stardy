import React from 'react';
import styled from "styled-components";
import kakaoimg from '../images/kakao.png'

export default function KaKaoButton() {

  return (
    <KaKaoWrap>
    <KaKaoBtn href="/oauth2/authorization/kakao%22%3E카카오 로그인">
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

