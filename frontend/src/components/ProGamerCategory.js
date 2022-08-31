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
      .then((res) => {
        setGamers(...gamers, res.data.data.content);
      })
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
            checkHandler(e.target.checked, item);
          }}
        />
        <Label htmlFor={item.nickname}>{item.name}</Label>
      </div>
    ));
  }

  return (
    <Wrap>
      <RedBox>
        <h2>Pro-Gamer</h2>
        <CategoryArea>
          {gamerBtn(littleGamers)}

          {moreView === false && gamers.length > 9 ? (
            <MoreViewBtn
              onClick={() => {
                setMoreView(!moreView);
              }}
            >
              ...
            </MoreViewBtn>
          ) : null}

          {moreView ? gamerBtn(bigGamers) : null}

          {moreView && gamers.length > 9 ? (
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
      </RedBox>
    </Wrap>
  );
}

const Wrap = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  padding: 5px;
  background-color: rgba(0, 0, 0, 0.8);
  border: 2px solid #331f1f;
  border-radius: 6px;
  margin: 0 auto;
  color: #b8bbcc;
  gap: 20px;
  h2 {
    width: 80%;
    margin: 0 auto;
    font-size: 40px;
  }
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

const CategoryArea = styled.div`
  width: 100%;
  margin: 0 auto;
  display: flex;
  flex-wrap: wrap;
  overflow: hidden;
  gap: 20px;

  input[type="checkbox"] {
    width: 0;
    height: 0;
    position: absolute;
  }
  input[type="checkbox"] + label {
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
  input[type="checkbox"]:checked + label {
    background: linear-gradient(0deg, transparent 50%, rgba(0, 0, 0, 0.6) 50%),
      red linear-gradient(90deg, red 0%, rgba(51, 31, 31, 0) 50%, red 100%);
    background-size: 1px 2px, cover;
    color: white;
  }
`;

const Input = styled.input``;
const Label = styled.label``;
const MoreViewBtn = styled.button`
  width: 50px;
  height: 42px;
  border: none;
  background-color: black;
  border-radius: 4px;
  font-size: 20px;
  color: #80ff66;
  cursor: pointer;
  border: 1px solid rgba(0, 204, 0, 0.6);
  box-shadow: 0 0 20px rgb(0 204 0 / 50%), inset 0 0 0 1px #000,
    inset 0 0 0 2px rgb(0 204 0 / 50%);
`;

const FolderBtn = styled.button`
  width: 50px;
  height: 42px;
  border: none;
  background-color: black;

  border: 1px solid rgba(0, 204, 0, 0.6);
  box-shadow: 0 0 20px rgb(0 204 0 / 50%), inset 0 0 0 1px #000,
    inset 0 0 0 2px rgb(0 204 0 / 50%);
  border-radius: 4px;
  font-size: 16px;
  color: #80ff66;
  cursor: pointer;
`;

const LectureArea = styled.div`
  width: 100%;
  margin: 0 auto;
  min-height: 1200px;
  padding-top: 50px;
`;
