import React from 'react';
import styled from 'styled-components';
import { Link } from "react-router-dom";
import Logo from "../favicon.ico"

const LinkItem = ({ active, children, to }) => (
  <Link to = {to} className={`menu-item ${active ? active : ""}`}> { children } </Link>
  );
  
  
  export default function Header() {
    return (
      <HeaderArea>
          <Wrap>
            <LogoArea> 
              <LinkItem to="/">
                <img src={Logo} alt="Logo" width="28" height="28"/>
                STARdy 
              </LinkItem> 
            </LogoArea>
            <UserNaviArea > 
              <Login>  Login </Login> | <LinkItem to="/mypage"> My Page </LinkItem> 
            </UserNaviArea>
            <HeaderNav>
              <LinkItem to="/progamer">Pro-gamer</LinkItem>
              <LinkItem to="/races">Races</LinkItem>
              <LinkItem to="/community">Community</LinkItem>
              <LinkItem to="/mylecture">내 강의실</LinkItem>
              <input />
            </HeaderNav>
        </Wrap>
      </HeaderArea>
  )
}



const Wrap = styled.div`
width: 80%;
margin: 0 auto;
position: relative;
`

const HeaderArea = styled.header`
background-color: black;
color: white;

`
const LogoArea = styled.div`
  width: 100%;
  font-size: 32px;
  text-align: center;
  font-weight: bold;
  color: white;
  margin-bottom: 8px;
`
const HeaderNav = styled.div`
  padding: 4px;
  display: flex;
  justify-content: space-between;
`

const UserNaviArea = styled.div`
  position: absolute;
  right: 4px;
  top: 12px;
  font-size: 14px;

`

const Login = styled.button`
border: 0;
outline: 0;
background-color: transparent;
color: white
`