import React, { useState } from "react";
import { CKEditor } from "@ckeditor/ckeditor5-react";
import ClassicEditor from "@ckeditor/ckeditor5-build-classic";
import styled from "styled-components";

export default function Editor() {
  const [channel, setChannel] = useState("");
  const [writing, setWriting] = useState([]);

  return (
    <Main>
      <Wrap>
        <Top>
          <h2>글쓰기</h2>
          <select
            id="channel"
            onChange={(e) => {
              setChannel(e.target.value);
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
                setWriting({ ...writing, title: e.target.value });
              }}
            />
            <CKEditor
              editor={ClassicEditor}
              data="<p>Hello from CKEditor 5!</p>"
              onChange={(event, editor) => {
                const data = editor.getData();
                return setWriting({
                  ...writing,
                  content: data,
                });
              }}
            />
          </form>
        </Top>

        <SubmitBtn
          type="submit"
          value="제출"
          onClick={(e) => {
            e.preventDefault();
            alert(
              `title: ${writing.title}
                  content: ${writing.content}
                  type: ${channel}`
            );
          }}
        ></SubmitBtn>
        <CancelBtn type="button" value="취소"></CancelBtn>
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
    border: 1px solid #dde0ea;
  }

  .ck-content {
    min-height: 600px;
  }
`;

const Title = styled.input`
  padding: 10px;
  width: 100%;
  height: 40px;
  font-size: 16px;
  border: 1px solid #dde0ea;
  margin-bottom: 20px;
`;

const SubmitBtn = styled.input`
  width: 144px;
  height: 48px;
  color: white;
  float: left;
  border-radius: 4px;
  border: none;
  font-size: 18px;
  background-color: #87c3a1;
  cursor: pointer;
`;

const CancelBtn = styled.input`
  width: 144px;
  height: 48px;
  float: right;
  border-radius: 4px;
  border: none;
  font-size: 18px;
  cursor: pointer;
`;
