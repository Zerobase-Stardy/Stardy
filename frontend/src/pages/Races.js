import React from 'react'
import styled from 'styled-components';
import {Outlet, Link} from "react-router-dom";


export default function Races() {

  const LinkItem = ({ children, to}) => (
    <Link to={to} style={{ margin: "10px" }}> 
      <HoverEffect>
      {" "}{children}{" "}
      </HoverEffect>
    </Link>
  );

  return (
    <BodyStyle>
      <PageCategory>
        <LinkItem to={"/races/terran"} > Terran </LinkItem>
        <LinkItem to={"/races/protoss"} > Protoss </LinkItem>
        <LinkItem to={"/races/zerg"} > Zerg </LinkItem>
      </PageCategory>
      <Outlet />
      
      
    </BodyStyle>
  )
}


const BodyStyle = styled.div`
  width: 60%;
  margin: 0 auto;
  display: flex;
  position: relative;
`
  
const PageCategory = styled.div`
  box-sizing: border-box;
  width: 144px;
  min-width: 120px;
  font-size: 28px;
  font-weight: bold;
  display: flex;
  flex-direction: column;
  padding: 5% 0 0 0;
  border-right: 2px solid gray;
  height: 80vh;
  margin: 5vh;
`
const HoverEffect = styled.div`

cursor: pointer; 
text-align: center;
  &:hover{  
    color : gray;
  }
`
