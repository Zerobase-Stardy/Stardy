import styled from "styled-components";
import { Link, Outlet } from "react-router-dom";
import { useSelector } from "react-redux";
import cookies from "react-cookies";

import axios from "axios";
export default function MyPage() {
  const LinkItem = ({ children, to }) => (
    <Link to={to} style={{ margin: "10px" }}>
      <HoverEffect> {children} </HoverEffect>
    </Link>
  );
  const header = useSelector((state) => state.userinfo.value.header);

  console.log(header);
  return (
    <BodyStyle>
      <PageCategory>
        <LinkItem to={"/mypage/profile"}> 프로필 </LinkItem>
        <LinkItem to={"/mypage/mylecture"}> 내 강의실 </LinkItem>
        <LinkItem to={"/mypage/attendance"}> 출석 체크 </LinkItem>
        <button
          onClick={() => {
            axios
              .get("https://dokuny.blog/members/me/withdrawal", {
                headers: header,
              })
              .then((res) => {
                cookies.remove("accessToken");
                cookies.remove("refreshToken");
                document.location.href = "/";
              });
          }}
        >
          회원탈퇴
        </button>
      </PageCategory>
      <Outlet />
    </BodyStyle>
  );
}

const BodyStyle = styled.div`
  width: 60%;
  margin: 0 auto;
  display: flex;
  position: relative;
`;

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
`;
const HoverEffect = styled.div`
  cursor: pointer;
  text-align: center;
  &:hover {
    color: gray;
  }
`;
