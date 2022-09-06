import React from "react";
import styled from "styled-components";
import { Link } from "react-router-dom";
import Pie from "./Pie";
export default function HomeCategory() {
  return (
    <MainArea>
      <Information>
        <InformationWrap>
          <InformationContentWrap>
            <Box>
              <Pie />
            </Box>
          </InformationContentWrap>
        </InformationWrap>
      </Information>
      <Wrap>
        <CategoryTitle>얼리버드 신규오픈 강의</CategoryTitle>
        <Category>
          <Box>
            <LectureImg />
            <LectureTitle />
          </Box>
          <Box>
            <LectureImg />
            <LectureTitle />
          </Box>
          <Box></Box>
        </Category>
      </Wrap>
    </MainArea>
  );
}

const MainArea = styled.main``;

const Wrap = styled.div`
  width: 70%;
  margin: 0 auto;
  display: grid;
  gap: 40px;
`;

const Information = styled.div`
  width: 100%;
  height: 400px;
  padding: 50px 0;
  background-image: url("https://d3muzvmm7fikyw.cloudfront.net/original/1X/e8de17b2ba0f32e4d1bf4d3485ee2e518245c825.jpg");
`;

const InformationWrap = styled.div`
  width: 70%;
  padding: 3px;
  background-color: rgba(0, 0, 0, 0.8);
  border: 2px solid #331f1f;
  border-radius: 6px;
  margin: 0 auto;
`;
const InformationContentWrap = styled.div`
  margin: 0 auto;
  width: 100%;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  gap: 20px;
  border: 1px solid #800000;
`;

const Category = styled.div`
  width: 100%;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  gap: 20px;
`;
const CategoryTitle = styled.h2`
  font-size: 24px;
`;

const Box = styled.div`
  flex-basis: 30%;
  height: 300px;
`;

const LectureImg = styled.div`
  width: 100%;
  height: 200px;
  background-color: brown;
`;

const LectureTitle = styled.div`
  width: 100%;
  height: 100px;
  background-color: darkgreen;
`;
