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
      <Wrap>
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
      </Wrap>
    </Main>
  );
}

const Main = styled.main`
  width: 100%;
  padding-top: 70px;
  background-color: #fbfbfb;
`;

const Wrap = styled.div`
  width: 60%;
  margin: 0 auto;
`;

const Top = styled.div`
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
      color: #87c3a1;
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
    padding: 10px 20px;
    box-sizing: border-box;
    position: relative;
    display: inline-block;
    border: solid 1px #ddd;
    border-radius: 4px;
    background-color: #fff;
    line-height: 140%;
    text-align: center;
    transition: border-color 0.15s ease-out, color 0.25s ease-out,
      background-color 0.15s ease-out, box-shadow 0.15s ease-out;
    cursor: pointer;
  }
  input[type="radio"]:checked + label {
    background-color: #87c3a1;
    color: #fff;
    border-color: #87c3a1;
  }
`;

const Bottom = styled.div`
  width: 100%;
  margin-top: 30px;
  border: 1px solid #dde0ea;
  border-radius: 4px;
  background-color: #fff;
`;
