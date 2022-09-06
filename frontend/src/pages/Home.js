import React from "react";
import styled from "styled-components";
import Carousel from "../Home/Carousel";
import HomeButton from "../Home/HomeButton";
import HomeCategory from "../Home/HomeContent";

export default function Home(props) {
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
