import React from 'react'
import { Link } from "react-router-dom";
import styled from "styled-components";


export default function Nav() {
  return (
    <Navigation>
        <Link to="/">Stardy</Link>
        <Link to="/mypage">Mypage</Link>
        <Link to="/progamer">Pro-Gamer</Link>
        <Link to="/races">Races</Link>
        <Link to="/community">Community</Link>
        <Link to="/mylecture">내 강의실</Link>
    </Navigation>
  )
}

const Navigation= styled.nav`
display: flex;
flex-direction: row-reverse;
justify-content: flex-end;

`