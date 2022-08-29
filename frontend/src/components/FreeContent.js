import React, { useEffect, useState } from "react";
import styled from "styled-components";
import axios from "axios";
import Page from "./Page";
import { ThreeDots } from "react-loader-spinner";
import { Link } from "react-router-dom";

export default function FreeContent() {
  const [freeBoard, setFreeBoard] = useState([]);
  const [count, setCount] = React.useState(0); //아이템 총 개수
  const [currentpage, setCurrentpage] = React.useState(1); //현재페이지
  const [postPerPage] = React.useState(10); //페이지당 아이템 개수
  const [indexOfLastPost, setIndexOfLastPost] = React.useState(0);
  const [indexOfFirstPost, setIndexOfFirstPost] = React.useState(0);
  const [currentPosts, setCurrentPosts] = React.useState(0);

  useEffect(() => {
    axios
      .get("https://jsonplaceholder.typicode.com/posts")
      .then((res) => setFreeBoard(res.data));
  }, []);

  useEffect(() => {
    setCount(freeBoard.length);
    setIndexOfLastPost(currentpage * postPerPage);
    setIndexOfFirstPost(indexOfLastPost - postPerPage);
    setCurrentPosts(freeBoard.slice(indexOfFirstPost, indexOfLastPost));
  }, [currentpage, indexOfFirstPost, indexOfLastPost, freeBoard, postPerPage]);

  const setPage = (e) => {
    setCurrentpage(e);
  };

  return (
    <>
      {currentPosts && freeBoard.length > 0 ? (
        currentPosts.map((list) => (
          <Link to={`${list.id}`}>
            <List key={list.id}>
              <Title> {list.title}</Title>
              <Content>
                <p>{list.body}</p>
              </Content>
            </List>
          </Link>
        ))
      ) : (
        <Center>
          <ThreeDots
            color="#ccff66"
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
  border: 1px solid rgba(221, 224, 234, 0.4);

  &:hover {
    background-color: rgba(147, 168, 237, 0.2);
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
