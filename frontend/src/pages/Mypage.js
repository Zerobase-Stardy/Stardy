import styled from "styled-components";
import { Link, Outlet } from "react-router-dom";

export default function MyPage(){

  const LinkItem = ({ children, to}) => (
    <Link to={to} style={{ margin: "10px" }}> 
      <HoverEffect>
      {" "}{children}{" "}
      </HoverEffect>
    </Link>
  );


    return(
        <BodyStyle>
            <PageCategory>
                <LinkItem to={"/mypage/profile"} > 프로필 </LinkItem>
                <LinkItem to={"/mypage/mylecture"} > 내 강의실 </LinkItem>
                <LinkItem to={"/mypage/attendance"} > 출석 체크 </LinkItem>
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
  box-sizing: border-box;
  width: 144px;
  min-width: 120px;
  font-size: 24px;
  font-weight: bold;
  display: flex;
  flex-direction: column;
  padding: 5% 0 0 0;
  border-right: 2px solid gray;
  height: 70vh;
  margin: 5vh;
`
const HoverEffect = styled.div`
cursor: pointer; 
text-align: center;
  &:hover{  
    color : gray;
  }
`

