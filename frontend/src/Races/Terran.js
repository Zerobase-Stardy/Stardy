import styled from "styled-components"
import { useState } from "react";




export default function Terran(){

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
                Terran
                <div style={{marginTop:"4px", fontSize: "13px", fontWeight: "normal", lineHeight: "16px"}}>
                은하계 외곽 지역에 있는 가혹한 불모의 환경에 적응하여 살아야 했던 테란은 그야말로 생존의 명수들이다. <br/>
                프로토스의 첨단 기술도 저그의 타고난 전투력도 갖추지 못한 테란의 군사력은 다양한 유닛의 조합으로 유지된다.<br/> 
                소모품에 불과하지만 효과적인 해병(Marine)부터 고도의 훈련을 쌓은 망령(Wraith) 전투기 조종사에 이르기까지 <br/>
                테란은 전우의 시체를 넘고 넘어 자신의 영토를 끝까지 수호하는 끈질긴 생명력을 보여 준다. <br/>
                이 젊은 종족은 자신들의 단점에도 불구하고 어떤 환경에서도 살아남을 수 있다는 굳은 신념을 지니고 있다.
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