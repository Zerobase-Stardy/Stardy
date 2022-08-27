import axios from "axios";
import React, { useEffect, useState } from "react";
import styled from "styled-components";
import ProGamerLecture from "../components/ProGamerLecture";

export default function ProGamerCategory() {
  const [gamers, setGamers] = useState([]);
  const [checkList, setCheckList] = useState([]);
  const [moreView, setMoreView] = useState(false);
  const littleGamers = gamers.filter((gamer, idx) => idx < 17);
  const bigGamers = gamers.filter((gamer, idx) => idx >= 17);

  useEffect(() => {
    axios
      .get("https://www.dokuny.blog/gamer/list")
      .then((res) => setGamers(...gamers, res.data.data))
      .catch((error) => {
        console.log(error);
      });
  }, []);

  function checkHandler(checked, item) {
    if (checked) {
      setCheckList([...checkList, item.name]);
    } else if (!checked) {
      setCheckList(checkList.filter((e) => e !== item.name)); //nickName 처럼 고유한 값 넣기
    }
  }

  function gamerBtn(array) {
    return array.map((item, idx) => (
      <div key={idx}>
        <Input
          name={item.nickname}
          type="checkbox"
          id={item.nickname}
          onChange={(e) => {
            console.log("ddd");
            checkHandler(e.target.checked, item);
          }}
        />
        <Label htmlFor={item.nickname}>{item.name}</Label>
      </div>
    ));
  }
  return (
    <Wrap>
      <h2>Pro-Gamer</h2>
      <CategoryArea>
        {gamerBtn(littleGamers)}

        {moreView === false && gamers.length > 17 ? (
          <MoreViewBtn
            onClick={() => {
              setMoreView(!moreView);
            }}
          >
            ...
          </MoreViewBtn>
        ) : null}

        {moreView ? gamerBtn(bigGamers) : null}

        {moreView && gamers.length > 17 ? (
          <FolderBtn
            onClick={() => {
              setMoreView(!moreView);
            }}
          >
            접기
          </FolderBtn>
        ) : null}
      </CategoryArea>
      <LectureArea>
        <ProGamerLecture checkList={checkList} />
      </LectureArea>
    </Wrap>
  );
}

const Wrap = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 20px;
  h2 {
    width: 80%;
    margin: 0 auto;
    font-size: 40px;
  }
`;

const CategoryArea = styled.div`
  width: 80%;
  margin: 0 auto;
  display: flex;
  flex-wrap: wrap;
  overflow: hidden;
  gap: 10px;

  input[type="checkbox"] {
    width: 0;
    height: 0;
    position: absolute;
  }
  input[type="checkbox"] + label {
    margin: 0;
    padding: 10px 24px;
    position: relative;
    display: inline-block;
    border-radius: 4px;
    background-color: #b8b8b8;
    color: #fff;
    line-height: 140%;
    text-align: center;
    transition: border-color 0.15s ease-out, color 0.25s ease-out,
      background-color 0.15s ease-out, box-shadow 0.15s ease-out;
    cursor: pointer;
  }
  input[type="checkbox"]:checked + label {
    background-color: #87c3a1;
    color: #fff;
    border-color: #87c3a1;
  }
`;

const Input = styled.input``;
const Label = styled.label``;
const MoreViewBtn = styled.button`
  width: 50px;
  height: 42px;
  border: none;
  background-color: #3198dc;
  border-radius: 4px;
  font-size: 20px;
  color: white;
  cursor: pointer;
`;

const FolderBtn = styled.button`
  width: 50px;
  height: 42px;
  border: none;
  background-color: #3198dc;
  border-radius: 4px;
  font-size: 16px;
  color: white;
  cursor: pointer;
`;

const LectureArea = styled.div`
  width: 80%;
  margin: 0 auto;
  min-height: 1200px;
  padding-top: 50px;
`;
