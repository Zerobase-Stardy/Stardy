import React from "react";
import styled from "styled-components";
import { Link } from "react-router-dom";
export default function HomeButton() {
  return (
    <MainArea>
      <Wrap>
        <LinkZone>
          <Link to="/progamer" className="progamer">
            Pro-gamer
          </Link>
          <Link to="races/terran" className="terran">
            Terran
          </Link>
          <Link to="races/zerg" className="zerg">
            Zerg
          </Link>
          <Link to="races/protoss" className="protoss">
            Protoss
          </Link>
        </LinkZone>
      </Wrap>
    </MainArea>
  );
}

const MainArea = styled.div`
  padding-top: 20px;
  background-image: url("https://static.starcraft.com/production/images/site/backdrops/backdrop-stars.890c5929ec65159852db3a0fab438e7aaa5c210f.jpg");
`;

const Wrap = styled.div`
  width: 70%;
  margin: 0 auto;
  display: grid;

  @media screen and (max-width: 1024px) {
    width: 90%;
  }
  @media screen and (max-width: 650px) {
    width: 100%;
  }
`;

const LinkZone = styled.div`
  width: 100%;
  margin: 0 auto;
  height: 200px;
  display: flex;
  justify-content: space-between;

  @media screen and (max-width: 1024px) {
    height: 150px;
  }
  @media screen and (max-width: 650px) {
    height: 120px;
  }

  @media screen and (max-width: 440px) {
    height: 100px;
  }

  .progamer {
    box-shadow: 0 15px 35px rgba(255, 255, 255, 0.4);
    &:hover {
      box-shadow: 0 15px 35px rgba(255, 255, 255, 0.8);
    }
  }
  .terran {
    box-shadow: 0 15px 35px rgba(105, 186, 255, 0.4);
    &:hover {
      box-shadow: 0 15px 35px rgba(105, 186, 255, 0.8);
    }
  }
  .zerg {
    box-shadow: 0 15px 35px rgba(250, 68, 68, 0.4);
    &:hover {
      box-shadow: 0 15px 35px rgba(250, 68, 68, 0.8);
    }
  }
  .protoss {
    box-shadow: 0 15px 35px rgba(255, 255, 134, 0.4);
    &:hover {
      box-shadow: 0 15px 35px rgba(255, 255, 134, 0.8);
    }
  }
  a {
    border-radius: 10px;
    width: 20%;
    height: 80px;
    border: none;
    text-align: center;
    line-height: 80px;
    /* box-shadow: 0 15px 35px rgba(0, 0, 0, 0.2); */
    transition: 0.25s;
    color: #fff;
    font-size: 24px;
    border: 1px solid #fff;

    @media screen and (max-width: 1024px) {
      width: 21%;
    }
    @media screen and (max-width: 670px) {
      width: 21%;
      font-size: 16px;
      height: 60px;
      line-height: 60px;
    }

    @media screen and (max-width: 440px) {
      width: 21%;
      font-size: 14px;
      height: 60px;
      line-height: 60px;
    }

    @media screen and (max-width: 338px) {
      font-size: 10px;
    }

    &:hover {
      transform: scale(1.2);
      cursor: pointer;
    }

    &:active {
      transform: scale(1.5);
    }
  }
`;
