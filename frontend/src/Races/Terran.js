import styled from "styled-components"
import { useEffect, useState } from "react";
import axios from "axios";
import { BsCoin } from "react-icons/bs";
import { useSelector } from "react-redux";




export default function Terran(){
  const header = useSelector((state) => state.userinfo.value.header);
  const login = useSelector((state) => state.userinfo.value.login);
  const [lectures, setLectures] = useState([])

  useEffect(() => {
    axios
    .get(`https://www.dokuny.blog/courses?race=terran`)
    .then((res) => {
          setLectures(...lectures, res.data.data.content)
    })
    .catch((err) => {
      console.log(err)
    })
  }, [])

    const [level, setLevel] = useState("Easy");

    const LevelSelectHandler = (e) => {
        setLevel(`${e.target.id}`)
    }

    function handleClick(e) {
      const lectureId  = e.currentTarget.id
      if(login === false) return window.alert("로그인이 필요합니다.")
      axios.post(`https://www.dokuny.blog/courses/${lectureId}/unlock`,{}, {
           headers: header
       }).
       then(()=>{
        window.alert(`강의가 해금되었습니다! 마이페이지에서 확인 할 수 있습니다`)

      }).
      catch((err) => {
        if(err.response.status === 500){
          window.alert("이미 소지한 강의입니다 마이페이지에서 확인해주세요")
        } else if (err.response.status === 401){
          window.alert("먼저 로그인을 해주세요")
        } else if(err.response.status === 403){
          window.alert("포인트가 부족합니다")
        }
      })
   }
 

    const levelVideo = lectures.map((data) => { 
      if(data.level === level){
      return(
 
          <LectureInfo key={data.id} onClick={handleClick} id={data.id}>
          <img src={data.thumbnailUrl} alt="썸네일" style={{ width: "100%"}}/>
          <div style={{fontSize:"16px", fontWeight:"700"}}> {data.title} </div>
          <Name>
            <p>{data.gamerName}</p>
          </Name>
          <Price>
            <span>
              <BsCoin /> :
            </span>
            &nbsp;
            <p>{data.price}</p>
          </Price>
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
                    <div id="Easy" onClick={LevelSelectHandler} style={{color: "#13cf3e",  marginRight: "8px"}}> Easy </div>
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
    
  &:hover {
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


const LectureArea = styled.div`
    width: 540px;
    display: flex;
    flex-wrap: wrap;
    align-items: center;
`
const Name = styled.div`
  p {
    font-size: 14px;
  }
`;

const Price = styled.div`
  font-size: 14px;
  display: flex;

  p {
    line-height: 23px;
    color: #ccff66;
  }
  span {
    font-size: 18px;
    color: #ccff66;
  }
`;
