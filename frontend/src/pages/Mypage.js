import styled from "styled-components";
import { Link, Outlet } from "react-router-dom";

export default function MyPage(){
    return(
        <BodyStyle>
            <PageCategory>
                <Link to={"/mypage/profile"} style={{ margin: " 10px " }}> 프로필 </Link>
                <Link to={"/mypage/mylecture"}  style={{ margin: " 10px " }}> 내 강의실 </Link>
                <Link to={"/mypage/attendance"}  style={{ margin: " 10px " }}> 출석 체크 </Link>
            </PageCategory>
            <Outlet />
        </BodyStyle>
    )
}


const BodyStyle = styled.div`
  width: 60%;
  margin: 0 auto;
  display: flex;
  position: relative;
`
  
const PageCategory = styled.div`
  width: 35%;
  font-size: 24px;
  font-weight: bold;
  display: flex;
  flex-direction: column;
  padding: 5%;
  border-right: 2px solid gray;
  height: 70vh;
  margin: 5vh;
`

