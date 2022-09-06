import React, { useEffect } from "react";
import axios from "axios";
import cookies from "react-cookies";
import styled from "styled-components";
import { ThreeDots } from "react-loader-spinner";
import { useNavigate } from "react-router-dom";

export default function KakaoHandle() {
  const expires = new Date();
  const navigate = useNavigate();
  let code = new URL(window.location.href).searchParams.get("code");
  expires.setHours(expires.getHours() + 1);

  useEffect(() => {
    axios
      .get(`https://dokuny.blog/auth/login/google?code=${code}`)
      .then((res) => {
        cookies.save("accessToken", res.data.data.accessToken, {
          path: "/",
          expires,
        });
        cookies.save("refreshToken", res.data.data.refreshToken, {
          path: "/",
          expires,
        });
        document.location.href = "/";
      }, []);
  });

  return (
    <Main>
      <BrownBox>
        <RedBox>
          <Center>
            <p>로그인 중 입니다.</p>
            <ThreeDots
              color="#80ff66"
              text-align="center"
              height={80}
              width={80}
            />
          </Center>
        </RedBox>
      </BrownBox>
    </Main>
  );
}

const Main = styled.div`
  width: 100%;
  min-height: 800px;
  padding: 200px 0;
  background-image: url("https://static.starcraft.com/production/images/site/backdrops/backdrop-stars.890c5929ec65159852db3a0fab438e7aaa5c210f.jpg");
`;

const BrownBox = styled.div`
  width: 90%;
  padding: 3px;
  background-color: rgba(0, 0, 0, 0.8);
  border: 2px solid #331f1f;
  border-radius: 6px;
  margin: 0 auto;
`;
const RedBox = styled.div`
  margin: 0 auto;

  box-sizing: border-box;
  width: 100%;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;

  border: 1px solid #800000;
  padding: 100px 0px;
`;
const Center = styled.div`
  width: 100%;
  svg {
    margin: 0 auto;
  }
  p {
    font-size: 30px;
    text-align: center;
    color: #80ff66;
  }
`;