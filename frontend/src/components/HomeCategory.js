import React from "react";
import styled from "styled-components";
import { Link } from "react-router-dom";
import Pie from "./Pie";
export default function HomeCategory() {
  return (
    <MainArea>
      <Wrap>
        <LinkZone>
          <Link to="/progamer" className="progamer">
            Pro-gamer
          </Link>
          <Link to="/terran" className="terran">
            Terran
          </Link>
          <Link to="/zerg" className="zerg">
            Zerg
          </Link>
          <Link to="protoss" className="protoss">
            Protoss
          </Link>
        </LinkZone>
        <Category>
          <Lecture>
            <Pie />
          </Lecture>
          <Lecture>
            <LectureImg />
            <LectureTitle />
          </Lecture>
          <Lecture>
            <LectureImg />
            <LectureTitle />
          </Lecture>
        </Category>
        <CategoryTitle>얼리버드 신규오픈 강의</CategoryTitle>
        <Category>
          <Lecture>
            <LectureImg />
            <LectureTitle />
          </Lecture>
          <Lecture>
            <LectureImg />
            <LectureTitle />
          </Lecture>
          <Lecture></Lecture>
        </Category>
      </Wrap>
    </MainArea>
  );
}

const MainArea = styled.main`
  margin-top: 50px;
`;

const Wrap = styled.div`
  width: 70%;
  margin: 0 auto;
  display: grid;
  gap: 40px;
`;

const LinkZone = styled.div`
  width: 100%;
  margin: 0 auto;
  height: 200px;
  display: flex;
  justify-content: space-between;

  a {
    width: 200px;
    height: 80px;
    border: none;
    text-align: center;
    line-height: 80px;
    border-radius: 15px;
    box-shadow: 0 15px 35px rgba(0, 0, 0, 0.2);
    transition: 0.25s;
    color: #fff;
    font-size: 24px;
    font-family: Galmuri;
    &:hover {
      letter-spacing: 2px;
      transform: scale(1.2);
      cursor: pointer;
    }

    &:active {
      transform: scale(1.5);
    }
  }
  .progamer {
    background-color: #2c3639;
  }
  .terran {
    background-color: #084b83;
  }
  .zerg {
    background-color: #613dc1;
  }
  .protoss {
    background-color: #eec170;
  }
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

const Lecture = styled.div`
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
