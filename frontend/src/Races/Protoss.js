import styled from "styled-components"
import { useEffect, useState } from "react";
import axios from "axios";
import { BsCoin } from "react-icons/bs";
import { useSelector,useDispatch } from "react-redux";
import { modal } from "../redux/loginSlice";



export default function Protoss(){
    const [lectures, setLectures] = useState([])
    const header = useSelector((state) => state.userinfo.value.header);
    const login = useSelector((state) => state.userinfo.value.login);
    const dispatch = useDispatch();

    useEffect(() => {
      axios
      .get(`https://www.dokuny.blog/courses?race=protoss`)
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

      if(login === false) {
        alert("로그인이 필요합니다.");
        dispatch(modal(true));
        e.preventDefault();
       } else {
         axios.post(`https://www.dokuny.blog/courses/${lectureId}/unlock`,{}, {
           headers: header
          }).
          then(()=>{
            window.alert(`강의가 해금되었습니다! 마이페이지에서 확인 할 수 있습니다`)   
          }).
          catch((err) => {
              window.alert(`${err.response.data.errorDescription}`)
          })
        }
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
                    <div id="Easy" onClick={LevelSelectHandler} style={{color: "#13cf3e", marginRight: "8px"}}> Easy </div>
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
