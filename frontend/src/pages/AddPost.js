import React, { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import axios from "axios";
import styled from "styled-components";
import Editor from "../components/Editor";

export default function Addpost() {
  const [boardKind, setBoardKind] = useState("");
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const header = useSelector((state) => state.userinfo.value.header);

  const handleText = (value) => {
    setContent(value);
  };

  return (
    <Main>
      <Effect />
      <Wrap>
        <RedBox>
          <Top>
            <h2>글쓰기</h2>
            <select
              id="boardKind"
              onChange={(e) => {
                setBoardKind(e.target.value);
              }}
            >
              <option value="">채널 선택</option>
              <option value="free">자유 게시판</option>
              <option value="suggest">빌드 건의</option>
            </select>
            <form>
              <Title
                type="text"
                placeholder="제목"
                onChange={(e) => {
                  setTitle(e.target.value);
                }}
              />
              <Editor writing={handleText} />
            </form>
          </Top>
          <Bottom>
            <SubmitBtn
              type="submit"
              value="제출"
              onClick={(e) => {
                axios
                  .post(
                    "https://www.dokuny.blog/posts",
                    {
                      title: title,
                      boardKind: boardKind,
                      content: content,
                    },

                    {
                      headers: header,
                    }
                  )
                  .then((res) => {
                    document.location.href = "/post";
                  })
                  .catch((err) => console.log(err));
              }}
            ></SubmitBtn>
            <CancelBtn
              type="button"
              value="취소"
              onClick={() => {
                document.location.href = "/post";
              }}
            />
          </Bottom>
        </RedBox>
      </Wrap>
    </Main>
  );
}

const Main = styled.main`
  width: 100%;
  padding-top: 70px;
  min-height: 900px;
  color: #b8bbcc;
  background-image: url("https://static.starcraft.com/production/images/site/backdrops/backdrop-stars.890c5929ec65159852db3a0fab438e7aaa5c210f.jpg");
`;
const Effect = styled.div`
  width: 593px;
  height: 53px;
  margin: 0 auto;
  background-image: url("https://static.starcraft.com/production/images/site/dividers/divider-terminal-detail.3a193b6d6e3a7d62cee253b2a245bbdd73bea9b6.png");
  @media screen and (max-width: 662px) {
    width: 300px;
  }
  @media screen and (max-width: 300px) {
    display: none;
  }
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

  @media screen and (max-width: 1024px) {
    width: 90%;
  }
`;

const RedBox = styled.div`
  margin: 0 auto;
  min-height: 300px;
  box-sizing: border-box;
  width: 100%;
  display: flex;
  flex-direction: column;
  flex-wrap: wrap;
  justify-content: space-between;
  border: 1px solid #800000;
  padding: 20px;

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
  margin-bottom: 50px;
  gap: 20px;
  h2 {
    width: 100%;
    font-size: 40px;
    font-weight: bold;
  }

  select {
    width: 110px;
    height: 40px;
    padding: 3px;
    font-size: 16px;
    background-color: rgba(0, 0, 0, 0.8);
    color: #00cc00;
    outline: none;
    border: 1px solid #800000;
  }
`;

const Title = styled.input`
  padding: 10px;
  width: 100%;
  height: 40px;
  font-size: 16px;
  background-color: rgba(0, 0, 0, 0.8);
  color: #00cc00;
  outline: none;
  border: 1px solid #800000;
  margin-bottom: 20px;

  &::placeholder {
    color: #ddff99;
  }
`;

const Bottom = styled.div``;

const SubmitBtn = styled.input`
  width: 144px;
  height: 48px;
  color: #00cc00;
  float: left;
  border-radius: 4px;
  font-size: 18px;
  border: 1px solid #800000;
  background-color: rgba(0, 0, 0, 0.1);
  cursor: pointer;

  &:hover {
    color: #ddff99;
  }
  @media screen and (max-width: 400px) {
    width: 100px;
  }
`;

const CancelBtn = styled.input`
  width: 144px;
  height: 48px;
  float: right;
  border-radius: 4px;
  background-color: rgba(0, 0, 0, 0.1);
  border: 1px solid #800000;
  color: #00cc00;
  font-size: 18px;
  cursor: pointer;

  &:hover {
    color: #ddff99;
  }

  @media screen and (max-width: 400px) {
    width: 100px;
  }
`;
