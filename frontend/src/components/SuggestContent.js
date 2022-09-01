import React, { useEffect, useState } from "react";
import styled from "styled-components";
import axios from "axios";
import Page from "./Page";
import { Link } from "react-router-dom";
import { ThreeDots } from "react-loader-spinner";

export default function FreeContent() {
  const [suggest, setSuggest] = useState([]);
  const [count, setCount] = React.useState(0); //아이템 총 개수
  const [currentpage, setCurrentpage] = React.useState(1); //현재페이지
  const [postPerPage] = React.useState(10); //페이지당 아이템 개수

  const [indexOfLastPost, setIndexOfLastPost] = React.useState(0);
  const [indexOfFirstPost, setIndexOfFirstPost] = React.useState(0);
  const [currentPosts, setCurrentPosts] = React.useState(0);

  useEffect(() => {
    axios
      .get("https://www.dokuny.blog/posts")
      .then((res) =>
        setSuggest(
          res.data.data.content.filter((data) => data.boardKind === "suggest")
        )
      );
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
          <Link to={`${list.id}`} key={list.id}>
            <List>
              <Top>
                <Title>{list.title}</Title>
                <Writer>{list.writer}</Writer>
              </Top>
              <Content>
                <p dangerouslySetInnerHTML={{ __html: list.content }} />
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

      <Page
        page={currentpage}
        count={count}
        setPage={setPage}
        postPerPage={postPerPage}
      />
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

  @media screen and (max-width: 650px) {
    height: 110px;
    padding: 10px;
  }
`;

const Center = styled.div`
  width: 100%;

  svg {
    margin: 0 auto;
  }
`;
const Top = styled.div`
  width: 100%;
  display: flex;
`;
const Title = styled.div`
  font-weight: bold;
  font-size: 24px;

  @media screen and (max-width: 662px) {
    font-size: 20px;
  }
  @media screen and (max-width: 350px) {
    font-size: 18px;
  }
`;

const Writer = styled.div`
  margin-left: auto;

  @media screen and (max-width: 662px) {
    font-size: 12px;
  }
  @media screen and (max-width: 450px) {
    font-size: 11px;
  }
`;

const Content = styled.div`
  width: 100%;
  height: 100px;
  img {
    display: none;
  }
  p {
    text-overflow: ellipsis;
    overflow: hidden;
    word-break: break-word;
    display: -webkit-box;
    -webkit-line-clamp: 2; // 원하는 라인수
    -webkit-box-orient: vertical;
  }

  @media screen and (max-width: 662px) {
    font-size: 12px;
  }
  @media screen and (max-width: 350px) {
    font-size: 10px;
  }
`;
