import React, { useEffect, useState } from "react";
import styled from "styled-components";
import axios from "axios";
import Page from "./Page";
import { ThreeDots } from "react-loader-spinner";

export default function FreeContent() {
  const [suggest, setsuggest] = useState([]);
  const [count, setCount] = React.useState(0); //아이템 총 개수
  const [currentpage, setCurrentpage] = React.useState(1); //현재페이지
  const [postPerPage] = React.useState(10); //페이지당 아이템 개수

  const [indexOfLastPost, setIndexOfLastPost] = React.useState(0);
  const [indexOfFirstPost, setIndexOfFirstPost] = React.useState(0);
  const [currentPosts, setCurrentPosts] = React.useState(0);

  useEffect(() => {
    axios
      .get("https://jsonplaceholder.typicode.com/todos")
      .then((res) => setsuggest(res.data));
  }, []);

  useEffect(() => {
    setCount(suggest.length);
    setIndexOfLastPost(currentpage * postPerPage);
    setIndexOfFirstPost(indexOfLastPost - postPerPage);
    setCurrentPosts(suggest.slice(indexOfFirstPost, indexOfLastPost));
  }, [currentpage, indexOfFirstPost, indexOfLastPost, suggest, postPerPage]);

  const setPage = (e) => {
    setCurrentpage(e);
  };

  return (
    <>
      {currentPosts && suggest.length > 0 ? (
        currentPosts.map((list) => (
          <List key={list.id}>
            <Title> {list.title}</Title>
            <Content>
              <p>{list.body}</p>
            </Content>
          </List>
        ))
      ) : (
        <Center>
          <ThreeDots
            color="#87c3a1"
            text-align="center"
            height={80}
            width={80}
          />
        </Center>
      )}

      <Page page={currentpage} count={count} setPage={setPage} />
    </>
  );
}

const List = styled.div`
  width: 100%;
  padding: 20px;
  height: 150px;
  display: flex;
  flex-direction: column;
  gap: 30px;
  border-bottom: 1px solid #dde0ea;

  &:hover {
    background-color: #edf5f1;
    transition: 0.5s;
  }
`;
const Center = styled.div`
  width: 100%;

  svg {
    margin: 0 auto;
  }
`;
const Title = styled.div`
  font-weight: bold;
  font-size: 24px;
  width: 100%;
`;

const Content = styled.div`
  width: 100%;
  height: 100px;

  p {
    text-overflow: ellipsis;
    overflow: hidden;
    word-break: break-word;
    display: -webkit-box;
    -webkit-line-clamp: 2; // 원하는 라인수
    -webkit-box-orient: vertical;
  }
`;
