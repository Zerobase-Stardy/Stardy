import React, { useState, useEffect } from "react";
import styled from "styled-components";
import { BsCoin } from "react-icons/bs";
import Page from "./Page";
import axios from "axios";

export default function ProGamerLecture(props) {
  const [lectures, setLectures] = useState([]);
  const [count, setCount] = useState(0); //아이템 총 개수
  const [currentpage, setCurrentpage] = useState(1); //현재페이지
  const [postPerPage] = React.useState(12); //페이지당 아이템 개수
  const [indexOfLastPost, setIndexOfLastPost] = useState(0);
  const [indexOfFirstPost, setIndexOfFirstPost] = useState(0);
  const [currentPosts, setCurrentPosts] = useState(0);
  const { checkList } = props;

  // useEffect(() => {
  //   axios
  //     .get("https://jsonplaceholder.typicode.com/photos")
  //     .then((res) => setLectures(res.data));
  // }, []);

  useEffect(() => {
    axios
      .get("https://www.dokuny.blog/course/courses")
      .then((res) => setLectures(res.data.data.content));
  }, []);

  useEffect(() => {
    setCount(lectures.length);
    setIndexOfLastPost(currentpage * postPerPage);
    setIndexOfFirstPost(indexOfLastPost - postPerPage);
    setCurrentPosts(lectures.slice(indexOfFirstPost, indexOfLastPost));
  }, [currentpage, indexOfFirstPost, indexOfLastPost, lectures, postPerPage]);

  const setPage = (e) => {
    setCurrentpage(e);
  };

  function lectureArea(data) {
    return (
      <LectureArea key={data.id}>
        <Thumbnail>
          <img src={data.thumbnailUrl} alt="thumblink" />
        </Thumbnail>
        <Title>{data.title}</Title>
        <Name>
          <p>{data.gamerName}</p>
        </Name>
        <Price>
          <span>
            <BsCoin /> :
          </span>
          &nbsp;
          <p>{data.price}</p>
        </Price>
      </LectureArea>
    );
  }

  return (
    <>
      <Wrap>
        {checkList.length === 0 ? (
          <>{currentPosts && currentPosts.map((data) => lectureArea(data))}</>
        ) : (
          currentPosts
            .filter((data) => checkList.includes(data.gamerName))
            .map((data) => lectureArea(data))
        )}
      </Wrap>

      {checkList.length === 0 ? (
        <Page page={currentpage} count={count} setPage={setPage} />
      ) : (
        <Page
          page={currentpage}
          count={
            currentPosts &&
            currentPosts.filter((data) => checkList.includes(data.gamerName))
              .length
          }
          setPage={setPage}
        />
      )}
    </>
  );
}

const Wrap = styled.div`
  width: 100%;
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: space-between;
`;

const LectureArea = styled.div`
  width: 23%;
  margin-bottom: 30px;
  display: flex;
  flex-direction: column;
  transition: 0.25s;

  @media screen and (max-width: 1024px) {
    width: 31%;
  }

  @media screen and (max-width: 800px) {
    width: 45%;
  }

  padding: 5px;
  gap: 10px;
  img {
    width: 100%;
  }

  &:hover {
    transform: scale(1.1);
    border-radius: 5px;
    border-color: #ccff66;
    box-shadow: 0 0 10px #ccff66, inset 0 0 0 1px #000, inset 0 0 0 2px #ccff66;
    color: #ccff66;
  }

  &:active {
    transform: scale(1.2);
  }
`;

const Thumbnail = styled.div``;
const Title = styled.div`
  font-size: 16px;
  font-weight: 700;
`;

const Name = styled.div`
  p {
    font-size: 14px;
  }
`;

const Price = styled.div`
  font-size: 14px;
  display: flex;

  p {
    line-height: 23px;
    color: #ccff66;
  }
  span {
    font-size: 18px;
    color: #ccff66;
  }
`;
