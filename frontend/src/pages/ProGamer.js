import React from "react";
import styled from "styled-components";
import ProGamerCategory from "../components/ProGamerCategory";

export default function ProGamer() {
  return (
    <Main>
      <Top>
        <ProGamerCategory />
      </Top>
    </Main>
  );
}

const Main = styled.main`
  width: 80%;
  margin: 0 auto;
  padding: 70px 0 100px 0;
`;

const Top = styled.div`
  width: 100%;
`;
