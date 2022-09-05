import React, { useEffect, useState } from 'react';
import styled from 'styled-components'
import { useSelector } from "react-redux";
import axios from 'axios';
import { HiStar } from "react-icons/hi";
import { Link } from "react-router-dom";

export default function Bookmark() {
  const header = useSelector((state) => state.userinfo.value.header);
  const [bookmark, setBookmark] = useState([])
  
  useEffect(() => {
    axios
    .get("https://www.dokuny.blog/members/me/courses?bookmark=true", {
      headers: header,
    }).then((res) => {
      const cutLecture =  [...res.data.data.content].slice(0,6)
      setBookmark([ ...cutLecture])
    }).catch((err) => {
      console.log(err)
    })
  }, [])
        

    const bookmarkLecture = bookmark.map((video) => {
        return (
          <Link to={`/classRoom/${video.courseId}`}>
            <LectureBox key={video.courseId} id={video.courseId}>
                <img src={video.thumbnail} alt="썸네일" style={{width:"100%", marginBottom: "4px"}}/>
                <div style={{fontSize:"14px"}}> {video.title} </div>
                <Star> <HiStar style={{color: "yellow", width: "16px", height: "16px"}}/> </Star>
            </LectureBox>
          </Link>
        )
    })

  return (
    <LectureBody>
      <VideoPackage>
        {bookmarkLecture}
      </VideoPackage>
    </LectureBody>
  )
}


const LectureBody = styled.div`
  min-width: 505px;
  font-size: 16px;
  font-weight: bold;
  margin: 8% 0 0 0;

`

const VideoPackage = styled.div`
  box-sizing: border-box;
  display: flex;
  width: 540px;
  flex-wrap: wrap;
  margin: 4px 0 4px 0;
`


const LectureBox = styled.div`
  width: 160px;
  margin: 0 16px 16px 0;
  cursor: pointer;
  position: relative;
  :hover {
    transform: scale(1.1);
    border-radius: 5px;
    border-color: #ccff66;
    box-shadow: 0 0 10px #ccff66, inset 0 0 0 1px #000, inset 0 0 0 2px #ccff66;
    color: #ccff66;
  }

  &:active {
    transform: scale(1.2);
  }

`

const Star = styled.div`
  position: absolute;
  top: 0;
  right: 0;
`