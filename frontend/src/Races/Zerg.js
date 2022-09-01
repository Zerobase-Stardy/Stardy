import styled from "styled-components"
import { useState } from "react";




export default function Zerg(){

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
            <LectureInfo key={data.gamerId}>
            <img src={data.thumblink} alt="썸네일" style={{ width: "100%"}}/>
            <div> {data.title} </div>
            </LectureInfo>

        ) 
        }}) 

    return(
        <Wrap>
            <RaceHeader>
                Zerg
                <div style={{marginTop:"4px", fontSize: "13px", fontWeight: "normal", lineHeight: "16px"}}>
                저그는 초월체에 의해 군단에 흡수된 몇 가지 종류의 동물들로 이루어져 있다. <br/>
                이들 생명체, 혹은 종은 각자의 효율적인 살인 병기 역할에 맞게 선택적으로 진화되어 <br/>
                궁극적인 힘을 얻으려는 저그의 목적에 봉사한다. <br/>
                저그는 우리가 흔히 아는 과학 기술을 사용하지는 않지만,<br/>
                고도로 진화한 무기와 갑피는 어떤 종족이 개발한 최첨단 장비와도 견줄 만하다.<br/>
                이러한 생체 진화는 초월체에 대한 절대적인 충성심과 함께 저그를 우주에서 가장 두려운 존재 중 하나로 만들었다.
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