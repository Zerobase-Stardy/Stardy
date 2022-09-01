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
    box-shadow: 0 15px 35px rgba(218, 33, 150, 0.4);
    &:hover {
      box-shadow: 0 15px 35px rgba(218, 33, 150, 0.8);
    }
  }
  .protoss {
    box-shadow: 0 15px 35px rgba(255, 255, 134, 0.4);
    &:hover {
      box-shadow: 0 15px 35px rgba(255, 255, 134, 0.8);
    }
  }
  a {
    width: 300px;
    height: 80px;
    border: none;
    text-align: center;
    line-height: 80px;
    /* box-shadow: 0 15px 35px rgba(0, 0, 0, 0.2); */
    transition: 0.25s;
    color: #fff;
    font-size: 24px;
    border: 2px solid #fff;

    &:hover {
      letter-spacing: 2px;
      transform: scale(1.2);
      cursor: pointer;
    }

    &:active {
      transform: scale(1.5);
    }
  }
`;
