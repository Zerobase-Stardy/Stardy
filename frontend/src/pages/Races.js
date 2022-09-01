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
    <Main>

      <BodyStyle>
        <RedBox>
          <PageCategory>
            <LinkItem to={"/races/terran"} > Terran </LinkItem>
            <LinkItem to={"/races/protoss"} > Protoss </LinkItem>
            <LinkItem to={"/races/zerg"} > Zerg </LinkItem>
          </PageCategory>
          <Outlet />
        
        
        </RedBox>
      </BodyStyle>
    </Main>
  )
}

const Main = styled.main`
  width: 100%;
  background-image: url("https://static.starcraft.com/production/images/site/backdrops/backdrop-stars.890c5929ec65159852db3a0fab438e7aaa5c210f.jpg");
  margin: 0 auto;
  padding: 70px 0 100px 0;
`


const BodyStyle = styled.div`
  color: #B8BBCC;
  width: 70%;
  margin: 0 auto;
  position: relative;
  background-color: rgba(0, 0, 0, 0.8);
  border: 2px solid #331f1f;
  border-radius: 6px;
  padding: 5px;
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
  border-right: 2px solid white;
  height: 757px;
  margin: 8px 0 0 8px;
  font-family: NanumBold;
`
const HoverEffect = styled.div`

cursor: pointer; 
text-align: center;
  &:hover{  
    color : gray;
  }
`
const RedBox = styled.div`
box-sizing: border-box;
width: 100%;
display: flex;
border: 1px solid #800000;
`