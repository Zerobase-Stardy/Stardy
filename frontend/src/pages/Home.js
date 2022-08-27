import React from "react";
import styled from "styled-components";
import Carousel from "../components/Carousel";
import HomeButton from "../components/HomeButton";
import HomeCategory from "../components/HomeCategory";

export default function Home() {
  return (
    <Main>
      <Carousel />
      <HomeButton />
      <HomeCategory />
    </Main>
  );
}

const Main = styled.div`
  background-color: #121212;
`;
