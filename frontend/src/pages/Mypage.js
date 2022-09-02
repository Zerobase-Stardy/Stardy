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

    <Main>

      <BodyStyle>
        <RedBox>

          <PageCategory>
            <LinkItem to={"/mypage/profile"}> 프로필 </LinkItem>
            <LinkItem to={"/mypage/bookmark"}> 강의 즐겨찾기 </LinkItem>
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
        </RedBox>
      </BodyStyle>
    </Main>
  );
}

const Main = styled.div`
  width: 100%;
  padding: 70px 0 100px 0;
  background-image: url("https://static.starcraft.com/production/images/site/backdrops/backdrop-stars.890c5929ec65159852db3a0fab438e7aaa5c210f.jpg");
`

const BodyStyle = styled.div`
  width: 70%;
  margin: 0 auto;
  position: relative;
  background-color: rgba(0, 0, 0, 0.8);
  border: 2px solid #331f1f;
  border-radius: 6px;
  padding: 5px;
  color: #B8BBCC;
  font-family: NanumBold;
`;
const RedBox = styled.div`
box-sizing: border-box;
width: 100%;
display: flex;
border: 1px solid #800000;
`


const PageCategory = styled.div`
  box-sizing: border-box;
  width: 170px;
  min-width: 120px;
  font-size: 24px;
  font-weight: bold;
  display: flex;
  flex-direction: column;
  padding: 5% 4px 0 0;
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
