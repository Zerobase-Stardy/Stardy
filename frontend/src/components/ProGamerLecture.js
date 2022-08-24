import React, { useState } from "react";
import styled from "styled-components";

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
      name: "임요한",
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
      name: "임요한",
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
      name: "임요한",
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
      <Name>{data.name}</Name>
      <Price>{data.price}</Price>
    </LectureArea>
  ));

  const filterLecture = datas.filter((data) => checkList.includes(data.name));

  const filterView = filterLecture.map((data) => (
    <LectureArea key={data.gamerId}>
      <Thumbnail>
        <img src={data.thumblink} alt="thumblink" />
      </Thumbnail>
      <Title>{data.title}</Title>
      <Name>{data.name}</Name>
      <Price>{data.price}</Price>
    </LectureArea>
  ));
  console.log(filterLecture.length === 0 ? "비워짐" : "꽉참");

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
  margin-bottom: 20px;
  display: flex;
  flex-direction: column;
  gap: 10px;
  img {
    width: 100%;
  }
`;

const Thumbnail = styled.div``;
const Title = styled.div`
  font-size: 18px;
  font-weight: bold;
`;

const Name = styled.div``;

const Price = styled.div``;
