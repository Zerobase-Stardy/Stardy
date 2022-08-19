import React from 'react'
import styled from "styled-components";



export default function HomeCategory() {
  return (
    <MainArea>
    <Wrap>
        <CategoryTitle>입문자라고 쫄지 말자</CategoryTitle>
        <Category>
        <Lecture><LectureImg>이미지</LectureImg><LectureTitle>강의 제목</LectureTitle></Lecture>
        <Lecture><LectureImg /><LectureTitle /></Lecture>
        <Lecture><LectureImg /><LectureTitle /></Lecture>
    </Category>
    <CategoryTitle>종족별로 골라듣자</CategoryTitle>
    <Category>
    <Lecture><LectureImg /><LectureTitle /></Lecture>
    <Lecture><LectureImg /><LectureTitle /></Lecture>
    <Lecture><LectureImg /><LectureTitle /></Lecture>
    </Category>

    <CategoryTitle>종족별로 골라듣자</CategoryTitle>
    <Category>
    <Lecture><LectureImg /><LectureTitle /></Lecture>
    <Lecture><LectureImg /><LectureTitle /></Lecture>
    <Lecture><LectureImg /><LectureTitle /></Lecture>
    </Category>
    </Wrap>
        
    </MainArea>
  )
}

const MainArea = styled.main`

`

const Wrap = styled.div`
width: 80%;
margin: 0 auto;
display: grid;
gap: 40px
`

const Category = styled.div`
width: 100%;
display: flex;
flex-wrap: wrap;
gap: 20px;
`
const CategoryTitle = styled.h2`
font-size: 30px;
font-weight: bold;
`

const Lecture = styled.div`
flex-basis: 30%;
height: 300px;
background-color: pink;
`

const LectureImg = styled.div`
width: 100%;
height: 200px;
background-color: brown;
`

const LectureTitle = styled.div`
width: 100%;
height: 100px;
background-color:darkgreen;
`