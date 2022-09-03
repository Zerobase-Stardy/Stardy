import React, { useEffect, useState } from 'react';
import styled from 'styled-components'
import { useSelector } from "react-redux";
import { HiStar } from "react-icons/hi";
import axios from 'axios';


export default function Bookmark() {
  const header = useSelector((state) => state.userinfo.value.header);
  const [myvideo, setMyVideo] = useState([]);
  const [star, setStar] = useState([]);
  const [bookmarkList, setbookmarkList] = useState([]);
  useEffect(() => {
    axios
    .get("https://www.dokuny.blog/members/me/courses?size=9", {
      headers: header,
    })
    .then((res) => { 
      setMyVideo( [...res.data.data.content])
    })
    .catch((err) => console.log(err))
  }, [])

  
  function handlebookmark(e){
    const lectureId  = e.currentTarget.id
    axios.post(`https://www.dokuny.blog/members/me/courses/${lectureId}/bookmark`,{}, {
        headers: header
        })
        .then((res) => {
          if(res.data.data.bookmark){
            window.alert("즐겨찾기로 등록되었습니다")
          } else {
            window.alert("즐겨찾기가 해제되었습니다.")
          }
        })
        .catch((err) => {
            console.log(err)
        })
  }

  axios
  .get("https://www.dokuny.blog/members/me/courses?bookmark=true", {
    headers: header,
  }).then((res) => {
    setbookmarkList(res.data.data.content)
  }).catch((err) => {
    console.log(err)
  })

  useEffect(() =>{
    let lecId = [];
    bookmarkList.map(lec => lecId.push(lec.courseId))
    setStar([...lecId])
  }, bookmarkList) 
  
  const userVideo = myvideo.map((video) => {
    return (
      <LectureBox key={video.courseId} id={video.courseId} onClick={handlebookmark}>
          <img src={video.thumbnail} alt="썸네일" style={{width:"100%", marginBottom: "4px"}}/>
          <div style={{fontSize:"14px"}}> {video.title} </div>
          { star.includes(video.courseId) ? <Star> <HiStar style={{color: "yellow", width: "16px", height: "16px"}}/> </Star>: null }
          </LectureBox>
      )
    })


    
  return (
    <LectureBody>
      클릭시 즐겨찾기 강의로 등록되며, 동일 강의 재 클릭시 즐겨찾기 해제 됩니다.
      <VideoPackage>
        { userVideo }
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
  maring: 8px;
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