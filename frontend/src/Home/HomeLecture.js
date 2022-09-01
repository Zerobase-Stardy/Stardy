import React, { useEffect, useState } from "react";
import styled from "styled-components";
import axios from "axios";
import { useSelector, useDispatch } from "react-redux";
import { modal } from "../redux/loginSlice";

export default function HomeLecture() {
  const [lectures, setLectures] = useState([]);
  const login = useSelector((state) => state.userinfo.value.login);
  const dispatch = useDispatch();

  useEffect(() => {
    axios.get("https://www.dokuny.blog/courses").then((res) => {
      setLectures(res.data.data.content);
    });
  }, []);

  function homeLayout(data) {
    return (
      <GreenBox
        key={data.title}
        onClick={() => {
          if (login !== true) {
            alert("로그인 해주세요");
            dispatch(modal(true));
          } else {
            document.location.href = `/Classroom/${data.id}`;
          }
        }}
      >
        <Img>
          <img src={data.thumbnailUrl} alt="강의" />
        </Img>
        <Content>
          <Title>{data.title}</Title>
          <Gamer>{data.gamerName}</Gamer>
          <p>{data.level}</p>
        </Content>
      </GreenBox>
    );
  }

  return (
    <MainArea>
      <StarBackground>
        <Effect />
        <BrownBox>
          <RedBox>
            <h2>입문자 신규 강의</h2>
            {lectures.length > 0
              ? lectures
                  .filter((e) => e.level === "Easy")
                  .map((data, idx) => idx < 3 && homeLayout(data))
              : null}
          </RedBox>
        </BrownBox>
      </StarBackground>
      <StarBackground>
        <Effect />
        <BrownBox>
          <RedBox>
            <h2>숙련자 신규 강의</h2>
            {lectures.length > 0
              ? lectures
                  .filter((e) => e.level === "Hard")
                  .map((data, idx) => idx < 3 && homeLayout(data))
              : null}
          </RedBox>
        </BrownBox>
      </StarBackground>
    </MainArea>
  );
}

const MainArea = styled.div``;
const Effect = styled.div`
  width: 593px;
  height: 53px;
  margin: 0 auto;
  background-image: url("https://static.starcraft.com/production/images/site/dividers/divider-terminal-detail.3a193b6d6e3a7d62cee253b2a245bbdd73bea9b6.png");
  @media screen and (max-width: 662px) {
    width: 300px;
  }
  @media screen and (max-width: 300px) {
    display: none;
  }
`;
const StarBackground = styled.div`
  width: 100%;
  padding: 50px 0;
  background-image: url("https://static.starcraft.com/production/images/site/backdrops/backdrop-stars.890c5929ec65159852db3a0fab438e7aaa5c210f.jpg");
`;

const BrownBox = styled.div`
  @media screen and (max-width: 1024px) {
    width: 90%;
  }

  width: 70%;
  padding: 3px;
  background-color: rgba(0, 0, 0, 0.8);
  border: 2px solid #331f1f;
  border-radius: 6px;
  margin: 0 auto;
  color: #b8bbcc;
`;

const RedBox = styled.div`
  margin: 0 auto;
  box-sizing: border-box;
  width: 100%;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  border: 1px solid #800000;
  padding: 20px 20px;

  h2 {
    width: 100%;
    color: #b8bbcc;
    font-size: 24px;
    margin-bottom: 30px;
    text-align: center;
  }

  @media screen and (max-width: 800px) {
    flex-direction: column;
    gap: 20px;
  }
`;

const GreenBox = styled.div`
  width: 30%;
  border: 1px solid rgba(221, 224, 234, 0.4);

  &:hover {
    border-color: #ccff66;
    box-shadow: 0 0 10px #ccff66, inset 0 0 0 1px #000, inset 0 0 0 2px #ccff66;
    color: #ccff66;
  }

  @media screen and (max-width: 1024px) {
    width: 30%;
    margin-bottom: 20px;
  }

  @media screen and (max-width: 800px) {
    width: 70%;
    margin: 0 auto;
  }

  @media screen and (max-width: 600px) {
    width: 80%;
    margin: 0 auto;
  }
`;

const Img = styled.div`
  width: 100%;
  height: 180px;
  border: 1px solid rgba(221, 224, 234, 0.4);

  img {
    width: 100%;
    height: 100%;
  }
`;
const Content = styled.div`
  width: 100%;
  padding: 20px;
  display: grid;
  gap: 20px;
`;
const Title = styled.div`
  width: 100%;
  font-size: 18px;
  font-family: NanumBold;
`;

const Gamer = styled.div`
  width: 100%;
  font-size: 16px;
`;
