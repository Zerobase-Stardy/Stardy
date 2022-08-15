import React ,{ useEffect }from 'react';
import styled from "styled-components";
// import {KAKAO_AUTH_URL} from "../OAuth"
import kakaoimg from '../images/kakao.png'


export default function KaKaoButton() {

  let code = new URL(window.location.href).searchParams.get('code')
  const KAKAO_URL = process.env.REACT_APP_KAKAO_AUTH_URL;
  
  return (
    <KaKaoWrap>
    <KaKaoBtn href={KAKAO_URL}>
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
