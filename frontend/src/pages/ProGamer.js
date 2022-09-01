import React from "react";
import styled from "styled-components";
import ProGamerCategory from "../components/ProGamerCategory";

export default function ProGamer() {
  return (
    <Main>
      <Wrap>
        <ProGamerCategory />
      </Wrap>
    </Main>
  );
}

const Main = styled.main`
  width: 100%;
  background-image: url("https://static.starcraft.com/production/images/site/backdrops/backdrop-stars.890c5929ec65159852db3a0fab438e7aaa5c210f.jpg");
  margin: 0 auto;
  padding: 70px 0 100px 0;
`;

const Wrap = styled.div`
  width: 70%;
  margin: 0 auto;
`;
