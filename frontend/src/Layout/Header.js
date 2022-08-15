import React from 'react';
import styled from 'styled-components';
import { Link } from "react-router-dom";


const LinkItem = ({ active, children, to }) => (
  <Link to = {to} className={`menu-item ${active ? active : ""}`}> { children } </Link>
  );
  
  
  export default function Header() {
    return (
      <Wrap>
        <LogoArea> 
          <LinkItem to="/"> STARdy </LinkItem> 
        </LogoArea>
        <UserNaviArea > 
          <LinkItem to="/login"> Login </LinkItem> | <LinkItem to="/mypage"> My Page </LinkItem> 
        </UserNaviArea>
        <HeaderNav>
          <LinkItem to="/progamer">Pro-gamer</LinkItem>
          <LinkItem to="/races">Races</LinkItem>
          <LinkItem to="/community">Community</LinkItem>
          <input />
        </HeaderNav>
    </Wrap>
  )
}



const Wrap = styled.div`

background-color: black;
color: white;
position: relative;
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