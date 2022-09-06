import React from "react";
import styled from "styled-components";
import ProGamerCategory from "../components/ProGamerCategory";

export default function ProGamer() {
  return (
    <Main>
      <Wrap>
        <Effect />
        <ProGamerCategory />
      </Wrap>
    </Main>
  );
}

const Main = styled.main`
  width: 100%;
  background-image: url("https://static.starcraft.com/production/images/site/backdrops/backdrop-stars.890c5929ec65159852db3a0fab438e7aaa5c210f.jpg");
  margin: 0 auto;
  min-height: 1200px;
  padding: 70px 0 100px 0;
`;

const Effect = styled.div`
  width: 593px;
  height: 53px;
  margin: 0 auto;
  background-image: url("https://static.starcraft.com/production/images/site/dividers/divider-terminal-detail.3a193b6d6e3a7d62cee253b2a245bbdd73bea9b6.png");
  @media screen and (max-width: 662px) {
    width: 300px;
  }
  @media screen and (max-width: 300px) {
    display: none;
  }
`;

const Wrap = styled.div`
  width: 70%;
  margin: 0 auto;

  @media screen and (max-width: 1280px) {
    width: 90%;
  }
`;
