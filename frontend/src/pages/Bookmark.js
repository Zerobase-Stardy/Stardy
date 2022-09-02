import React, { useEffect, useState } from 'react';
import styled from 'styled-components'
import { useSelector } from "react-redux";
import axios from 'axios';
import { Link } from "react-router-dom";


export default function Bookmark() {
  const header = useSelector((state) => state.userinfo.value.header);
  const [myvideo, setMyVideo] = useState([]);
  const [check, setCheck] = useState(false)
  const [bookmark, setBookmark] = useState([])
  
  useEffect(() => {
    axios
    .get("https://www.dokuny.blog/members/me/courses", {
      headers: header,
    })
    .then((res) => {
      const cutLecture =  [...res.data.data.content].slice(0,6)
      setMyVideo( [...cutLecture])
    })
    .catch((err) => console.log(err))
  }, [])

  
  function handlebookmark(e){
    const lectureId  = e.currentTarget.id
    axios.post(`https://www.dokuny.blog/members/me/courses/${lectureId}/bookmark`,{}, {
        headers: header
        })
        .then(() => {
            window.alert("즐겨찾기로 등록되었습니다")
        })
        .catch((err) => {
            console.log(err)
        })
}
        
        const handleCheckBox = (e) => {
            setCheck()
        }
        
const NoBookmarking = (e) =>{
            const lectureId  = e.currentTarget.id
            if(check){
                axios.post(`https://www.dokuny.blog/members/me/courses/${lectureId}/bookmark`,{}, {
                    headers: header
                })
                .then(() => {
                    window.alert("즐겨찾기가 해제되었습니다")
                })
                .catch((err) => {
                    console.log(err)
                })
            } else {
                document.location.href = `/Classroom/${lectureId}`;
            }
        }
        
    const userVideo = myvideo.map((video) => {
        return (
            <LectureBox key={video.courseId} id={video.courseId} onClick={handlebookmark}>
            <img src={video.thumbnail} alt="썸네일" style={{width:"100%", marginBottom: "4px"}}/>
            <div style={{fontSize:"14px"}}> {video.title} </div>
            </LectureBox>
        )
    })

    axios
    .get("https://www.dokuny.blog/members/me/courses?bookmark=true", {
    headers: header,
    }).then((res) => {
        const cutLecture =  [...res.data.data.content].slice(0,6)
        setBookmark([ ...cutLecture])
    }).catch((err) => {
        console.log(err)
    })
        

    const bookmarkLecture = bookmark.map((video) => {
        return (
            <LectureBox key={video.courseId} id={video.courseId} onClick={NoBookmarking}>
                <img src={video.thumbnail} alt="썸네일" style={{width:"100%", marginBottom: "4px"}}/>
                <div style={{fontSize:"14px"}}> {video.title} </div>
            </LectureBox>
        )
    })

  return (
    <LectureBody>
      클릭시 즐겨찾기 강의로 등록됩니다.
      <VideoPackage>
        { userVideo }
      </VideoPackage>
      즐겨찾기 등록한 강의(체크후 클릭하면 즐겨찾기가 해제됩니다.)
      <input type='checkbox' value={ check } onClick={(e) =>
            setCheck(!e.target.value)
      }></input>
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