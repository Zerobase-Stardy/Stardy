import React, { useState } from "react";
import styled from "styled-components";
import { BsCoin } from "react-icons/bs";

export default function ProGamerLecture(props) {
  //   const [lectures, setLectures] = useState([]);
  const datas = [
    {
      gamerId: 1,
      title: "유영진 테란 강의",
      videoUrl: "http://www.youtube.com",
      thumblink: "http://i.ytimg.com/vi/wFDLVWEtkl8/mqdefault.jpg",
      comment: "설명",
      level: "입문(난이도)",
      race: "테란(종족)",
      price: 20,
      name: "임요환",
    },

    {
      gamerId: 2,
      title: "유영진 테란 강의",
      videoUrl: "http://www.youtube.com",
      thumblink: "http://i.ytimg.com/vi/wFDLVWEtkl8/mqdefault.jpg",
      comment: "설명",
      level: "입문(난이도)",
      race: "테란(종족)",
      price: 20,
      name: "임요환",
    },
    {
      gamerId: 3,
      title: "유영진 테란 강의",
      videoUrl: "http://www.youtube.com",
      thumblink: "http://i.ytimg.com/vi/wFDLVWEtkl8/mqdefault.jpg",
      comment: "설명",
      level: "입문(난이도)",
      race: "테란(종족)",
      price: 20,
      name: "임요환",
    },
    {
      gamerId: 4,
      title: "유영진 테란 강의",
      videoUrl: "http://www.youtube.com",
      thumblink: "http://i.ytimg.com/vi/wFDLVWEtkl8/mqdefault.jpg",
      comment: "설명",
      level: "입문(난이도)",
      race: "테란(종족)",
      price: 20,
      name: "유영진",
    },
    {
      gamerId: 5,
      title: "유영진 테란 강의",
      videoUrl: "http://www.youtube.com",
      thumblink: "http://i.ytimg.com/vi/wFDLVWEtkl8/mqdefault.jpg",
      comment: "설명",
      level: "입문(난이도)",
      race: "테란(종족)",
      price: 20,
      name: "유영진",
    },
    {
      gamerId: 6,
      title: "유영진 테란 강의",
      videoUrl: "http://www.youtube.com",
      thumblink: "http://i.ytimg.com/vi/wFDLVWEtkl8/mqdefault.jpg",
      comment: "설명",
      level: "입문(난이도)",
      race: "테란(종족)",
      price: 20,
      name: "유영진",
    },
    {
      gamerId: 7,
      title: "유영진 테란 강의",
      videoUrl: "http://www.youtube.com",
      thumblink: "http://i.ytimg.com/vi/wFDLVWEtkl8/mqdefault.jpg",
      comment: "설명",
      level: "입문(난이도)",
      race: "테란(종족)",
      price: 20,
      name: "저그킹",
    },
    {
      gamerId: 8,
      title: "유영진 테란 강의",
      videoUrl: "http://www.youtube.com",
      thumblink: "http://i.ytimg.com/vi/wFDLVWEtkl8/mqdefault.jpg",
      comment: "설명",
      level: "입문(난이도)",
      race: "테란(종족)",
      price: 20,
      name: "프로킹",
    },
    {
      gamerId: 9,
      title: "유영진 테란 강의",
      videoUrl: "http://www.youtube.com",
      thumblink: "http://i.ytimg.com/vi/wFDLVWEtkl8/mqdefault.jpg",
      comment: "설명",
      level: "입문(난이도)",
      race: "테란(종족)",
      price: 20,
    },
    {
      gamerId: 10,
      title: "유영진 테란 강의",
      videoUrl: "http://www.youtube.com",
      thumblink: "http://i.ytimg.com/vi/wFDLVWEtkl8/mqdefault.jpg",
      comment: "설명",
      level: "입문(난이도)",
      race: "테란(종족)",
      price: 20,
    },
  ];
  const { checkList } = props;

  const lecture = datas.map((data) => (
    <LectureArea key={data.gamerId}>
      <Thumbnail>
        <img src={data.thumblink} alt="thumblink" />
      </Thumbnail>
      <Title>{data.title}</Title>
      <Name>
        <p>{data.name}</p>
      </Name>{" "}
      <Price>
        <span>
          <BsCoin /> :
        </span>
        &nbsp;
        <p>{data.price}</p>
      </Price>
    </LectureArea>
  ));

  const filterLecture = datas.filter((data) => checkList.includes(data.name));

  const filterView = filterLecture.map((data) => (
    <LectureArea key={data.gamerId}>
      <Thumbnail>
        <img src={data.thumblink} alt="thumblink" />
      </Thumbnail>
      <Title>{data.title}</Title>
      <Name>
        <p>{data.name}</p>
      </Name>
      <Price>
        <span>
          <BsCoin /> :
        </span>
        &nbsp;
        <p>{data.price}</p>
      </Price>{" "}
    </LectureArea>
  ));

  return (
    <Wrap>
      {filterLecture.length === 0 ? <>{lecture}</> : <>{filterView}</>}
    </Wrap>
  );
}

const Wrap = styled.div`
  width: 100%;
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;

  gap: 2%;
`;

const LectureArea = styled.div`
  width: 23%;
  height: 250px;
  margin-bottom: 30px;
  display: flex;
  flex-direction: column;
  transition: 0.25s;

  padding: 10px;
  gap: 10px;
  img {
    width: 100%;
  }

  &:hover {
    transform: scale(1.1);
    background-color: #ff6e7f;
    border-radius: 8px;
    border: 1px white;
    color: white;

    p {
      color: white;
    }
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
    color: #606060;
  }
`;

const Price = styled.div`
  font-size: 18px;
  display: flex;

  p {
    line-height: 25px;
    color: #606060;
  }
  span {
    font-size: 20px;
    color: yellowgreen;
  }
`;
