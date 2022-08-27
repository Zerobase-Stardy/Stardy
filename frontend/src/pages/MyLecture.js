import React from 'react'
import styled from 'styled-components'

export default function MyLecture() {
    
  ImgBox.defaultProps = { src: "http://i.ytimg.com/vi/5oYQCn24Sk4/mqdefault.jpg" }


  return (
    <LectureBody>
      해금한 강의
      <VideoPackage>
        <ImgBox/>
        <ImgBox/>
        <ImgBox/>
        <ImgBox/>
        <ImgBox/>
        <ImgBox/>
      </VideoPackage>
      내가 들은 강의
      <VideoPackage>
        <ImgBox />
        <ImgBox />
        <ImgBox />
        <ImgBox />
        <ImgBox />
        <ImgBox />
      </VideoPackage>
    </LectureBody>
  )
}


const LectureBody = styled.div`
  min-width: 505px;
  padding: 7%;
  font-size: 20px;
  font-weight: bold;

`

const VideoPackage = styled.div`
  box-sizing: border-box;
  display: flex;
  width: 540px;
  flex-wrap: wrap;
  margin: 4px 0 4px 0;

`


const ImgBox = styled.img`
  margin: 5px;
  width: 160px;
`

