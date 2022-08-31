import React, { useState } from "react";
import styled from "styled-components";
import { Link } from "react-router-dom";
import { useLocation } from "react-router-dom";
import { useSelector } from "react-redux";
import NavHoverMenu from "../components/NavHoverMenu";
import cookies from "react-cookies";
import axios from "axios";

const LinkItem = ({ active, children, to }) => (
  <Link
    to={to}
    className={`menu-item ${active ? active : ""}`}
    style={{ position: "relative" }}
  >
    {" "}
    {children}{" "}
  </Link>
);

export default function Header(props) {
  const user = useSelector((state) => state.userinfo.value);
  const header = useSelector((state) => state.userinfo.value.header);

  const locationNow = useLocation();
  const [visible, setVisible] = useState({
    display: "none",
  });

  const seeDropDown = () => {
    setVisible({
      display: "block",
    });
  };

  const deleteDrop = () => {
    setVisible({
      display: "none",
    });
  };

  if (
    locationNow.pathname.includes("admin") ||
    locationNow.pathname.includes("classRoom") ||
    locationNow.pathname.includes("error")
  )
    return null;

  return (
    <HeaderArea>
      <Wrap>
        <LogoArea>
          <LinkItem to="/">STARDY</LinkItem>
        </LogoArea>
        <UserNaviArea>
          {user.login ? (
            <Logout
              onClick={() => {
                axios
                  .get("https://www.dokuny.blog/auth/logout", {
                    headers: header,
                  })
                  .then((respose) => {
                    console.log(respose);
                  })
                  .catch((err) => console.log(err));
                cookies.remove("accessToken");
                cookies.remove("refreshToken");
                document.location.href = "/";
              }}
            >
              Logout
            </Logout>
          ) : (
            <Login onClick={props.toggle}> Login </Login>
          )}
          {user.login ? (
            <LinkItem to="/mypage/profile">
              <span>|</span> &nbsp;&nbsp;<span className="mypage">My Page</span>
            </LinkItem>
          ) : null}
        </UserNaviArea>
        <HeaderBottom>
          <HeaderNav>
            <LinkItem to="/progamer">Pro-gamer</LinkItem>

            <div onMouseOver={seeDropDown} onMouseLeave={deleteDrop}>
              <LinkItem to="/races/terran">
                Races
                <NavHoverMenu style={visible} />
              </LinkItem>
            </div>
            <LinkItem to="/post">Community</LinkItem>
          </HeaderNav>

          {user.login ? (
            <UserName>{user.nickname}님 환영합니다</UserName>
          ) : null}
        </HeaderBottom>
      </Wrap>
    </HeaderArea>
  );
}

const Wrap = styled.div`
  width: 70%;
  margin: 0 auto;
  position: relative;
  color: #80ff66;
`;

const HeaderArea = styled.header`
  background-color: black;
  border-bottom: 1px solid #800000;
  color: white;
`;
const LogoArea = styled.div`
  width: 100%;
  font-size: 32px;
  text-align: left;
  color: rgba(255, 255, 255, 0.8);
  margin-bottom: 8px;
  font-family: Starcraft;

  a:hover {
    color: rgba(255, 255, 255, 1);
  }
`;
const HeaderBottom = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-between;
`;

const HeaderNav = styled.div`
  padding: 4px;
  display: flex;
  justify-content: space-between;
  width: 30%;
  min-width: 240px;

  a:hover {
    color: #ccff66;
  }
`;

const UserName = styled.div`
  font-size: 12px;
`;
const UserNaviArea = styled.div`
  position: absolute;
  right: 4px;
  top: 12px;
  font-size: 14px;

  .mypage:hover {
    color: #ccff66;
  }
`;

const Login = styled.button`
  border: 0;
  outline: 0;
  background-color: transparent;
  color: #80ff66;
  cursor: pointer;
  font-size: 14px;
  font-family: Nanum;

  &:hover {
    color: #ccff66;
  }
`;

const Logout = styled.button`
  border: 0;
  outline: 0;
  background-color: transparent;
  color: #80ff66;
  cursor: pointer;
  font-size: 14px;
  font-family: Nanum;

  &:hover {
    color: #ccff66;
  }
`;
