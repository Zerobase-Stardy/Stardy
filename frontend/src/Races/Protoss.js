import styled from "styled-components"
import { useState } from "react";




export default function Protoss(){

    const datas = [
        {
          gamerId: 1,
          title: "유영진 테란 강의",
          videoUrl: "http://www.youtube.com",
          thumblink: "http://i.ytimg.com/vi/wFDLVWEtkl8/mqdefault.jpg",
          comment: "설명",
          level: "Easy",
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
          level: "Easy",
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
          level: "Easy",
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
          level: "Easy",
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
          level: "Easy",
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
          level: "Easy",
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
          level: "Easy",
          race: "테란(종족)",
          price: 20,
          name: "저그킹",
        },
        {
          gamerId: 8,
          title: "유영진 테란 강의",
          videoUrl: "http://www.youtube.com",
          thumblink: "http://i.ytimg.com/vi/5oYQCn24Sk4/mqdefault.jpg",
          comment: "설명",
          level: "Hard",
          race: "테란(종족)",
          price: 20,
          name: "프로킹",
        },
        {
          gamerId: 9,
          title: "유영진 테란 강의",
          videoUrl: "http://www.youtube.com",
          thumblink: "http://i.ytimg.com/vi/5oYQCn24Sk4/mqdefault.jpg",
          comment: "설명",
          level: "Hard",
          race: "테란(종족)",
          price: 20,
        },
        {
          gamerId: 10,
          title: "유영진 테란 강의",
          videoUrl: "http://www.youtube.com",
          thumblink: "http://i.ytimg.com/vi/5oYQCn24Sk4/mqdefault.jpg",
          comment: "설명",
          level: "Hard",
          race: "테란(종족)",
          price: 20,
        },
      ];

    const [level, setLevel] = useState("Easy");

    const LevelSelectHandler = (e) => {
        setLevel(`${e.target.id}`)
        console.log(level)
    }

    const [lecture, setLecture] = useState([])


    const levelVideo = datas.map((data) => { 
        if(data.level === level){
        return(
            <LectureInfo>
            <img src={data.thumblink} alt="썸네일" style={{ width: "100%"}}/>
            <div> {data.title} </div>
            </LectureInfo>

        ) 
        }}) 

    return(
        <Wrap>
            <RaceHeader>
                Protoss
                <div style={{marginTop:"4px", fontSize: "13px", fontWeight: "normal", lineHeight: "16px"}}>
                적응력이 뛰어난 테란이나 야성적인 저그와는 달리 프로토스는 둔감하며 보수적이다. <br/>
                고도의 기술과 초능력을 가진 프로토스는 오랫동안 스스로를 은하계에서 가장 발달한 종족이라 자부하고 있었다.<br/>
                그들은 비록 인구가 많지는 않지만 로봇형 전투 장비로 부족한 전사들을 보충하고, 기술과 사이오닉 능력을 <br/>
                결합함으로써 가장 효율적인 전사들을 배출해 낼 수 있었다. 프로토스에게 약점이 있다면 <br/>
                그것은 그들이 변화를 싫어한다는 점일 것이다. 칼라의 교리는 조금도 융통성이 없을 뿐만 아니라, <br/>
                프로토스는 또 다시 내전에 빠질까 염려하여 교리에서 한 발짝이라도 벗어나는 것을 두려워하기 때문이다.
                </div>
                <LevelBox>
                    <div id="Easy" onClick={LevelSelectHandler} style={{color: "#13cf3e"}}> Easy </div>
                    <div id="Hard" onClick={LevelSelectHandler}  style={{color: "red"}}> Hard </div>
                </LevelBox>
            </RaceHeader>
            <LectureArea>
                {levelVideo}
            </LectureArea>
        </Wrap>
    )
}
const Wrap = styled.div`
  width: 100%;
  height: 100%;
  margin: 24px;
`
const RaceHeader = styled.div`
    padding-top: 4%;
    font-size: 28px;
    border-bottom: 1px solid white;
    height: 27vh;
    width: 100%;
    font-weight: bold;
    position: relative;
`

const LevelBox = styled.div`
    position: absolute;
    bottom: 4px;
    display: flex;
    justify-content: space-between;
    width: 80px;
    font-size: 18px;
    cursor: pointer;
    margin: 4px;
`

const LectureInfo = styled.div`
    width: 160px;
    height: 160px;
    margin: 8px;

`

const LectureArea = styled.div`
    width: 540px;
    display: flex;
    flex-wrap: wrap;
    align-items: center;
`