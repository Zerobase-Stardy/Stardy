import React, { useState } from "react";
import styled from "styled-components";
import { Link } from "react-router-dom";
import { BsPencilSquare } from "react-icons/bs";
import FreeContent from "../components/FreeContent";
import SuggestContent from "../components/SuggestContent";

export default function FreeBoard() {
  const [checked, setChecked] = useState("free");
  return (
    <Main>
      <Effect />

      <Wrap>
        <RedBox>
          <Top>
            <h2>Community</h2>
            <BoardName>
              <Radio>
                <Input
                  name="boardtype"
                  type="radio"
                  id="free"
                  onClick={(e) => {
                    setChecked(e.target.id);
                  }}
                  defaultChecked
                />
                <Label htmlFor={"free"}>자유 게시판</Label>

                <Input
                  name="boardtype"
                  type="radio"
                  id="suggest"
                  onClick={(e) => {
                    setChecked(e.target.id);
                  }}
                />
                <Label htmlFor={"suggest"}>빌드 건의</Label>
              </Radio>
              <Link to="write" style={{ marginLeft: "auto" }}>
                <BsPencilSquare />
              </Link>
            </BoardName>
          </Top>
          <Bottom>
            {checked === "free" ? <FreeContent /> : <SuggestContent />}
          </Bottom>
        </RedBox>
      </Wrap>
    </Main>
  );
}

const Main = styled.main`
  width: 100%;
  padding-top: 70px;
  color: #b8bbcc;
  background-image: url("https://static.starcraft.com/production/images/site/backdrops/backdrop-stars.890c5929ec65159852db3a0fab438e7aaa5c210f.jpg");
`;
const Effect = styled.div`
  width: 593px;
  height: 53px;
  margin: 0 auto;
  background-image: url("https://static.starcraft.com/production/images/site/dividers/divider-terminal-detail.3a193b6d6e3a7d62cee253b2a245bbdd73bea9b6.png");
`;
const Wrap = styled.div`
  width: 60%;
  margin: 0 auto;
  padding: 5px;
  background-color: rgba(0, 0, 0, 0.8);
  border: 2px solid #331f1f;
  border-radius: 6px;
  margin: 0 auto;
  color: #b8bbcc;
`;

const RedBox = styled.div`
  margin: 0 auto;
  box-sizing: border-box;
  width: 100%;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  border: 1px solid #800000;
  padding: 20px 70px;

  h2 {
    width: 100%;
    color: #b8bbcc;
    font-size: 24px;
    margin-bottom: 30px;
  }
`;

const Top = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 30px;
  h2 {
    width: 100%;
    font-size: 40px;
    font-weight: bold;
  }
`;

const BoardName = styled.div`
  display: flex;
  font-size: 18px;
  gap: 10px;
  text-align: center;
  line-height: 40px;

  svg {
    font-size: 40px;
    margin-right: 0;

    &:hover {
      color: #ccff66;
    }
  }
`;

const Input = styled.input``;
const Label = styled.label``;

const Radio = styled.div`
  input[type="radio"] {
    width: 0;
    height: 0;
    position: absolute;
  }
  input[type="radio"] + label {
    margin: 0;
    padding: 10px 32px;
    position: relative;
    font-size: 18px;
    display: inline-block;
    border-radius: 5px;
    border: 1px solid #cc0000;
    background: linear-gradient(0deg, transparent 50%, rgba(0, 0, 0, 0.6) 50%),
      #800000
        linear-gradient(
          90deg,
          #331f1f 0%,
          rgba(51, 31, 31, 0) 50%,
          #331f1f 100%
        );
    background-size: 1px 2px, cover;
    color: white;
    line-height: 140%;
    text-align: center;
    transition: border-color 0.15s ease-out, color 0.25s ease-out,
      background-color 0.15s ease-out, box-shadow 0.15s ease-out;
    cursor: pointer;
  }
  input[type="radio"]:checked + label {
    background: linear-gradient(0deg, transparent 50%, rgba(0, 0, 0, 0.6) 50%),
      red linear-gradient(90deg, red 0%, rgba(51, 31, 31, 0) 50%, red 100%);
    background-size: 1px 2px, cover;
    color: white;
  }
`;

const Bottom = styled.div`
  width: 100%;
  margin-top: 30px;
  border: 1px solid rgba(221, 224, 234, 0.4);
  border-radius: 4px;
  min-height: 1700px;
`;
