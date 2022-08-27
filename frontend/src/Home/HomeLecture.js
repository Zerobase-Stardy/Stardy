import React, { useEffect } from "react";
import styled from "styled-components";
import axios from "axios";

export default function HomeLecture() {
  return (
    <MainArea>
      <StarBackground>
        <Effect />
        <BrownBox>
          <RedBox>
            <h2>신규 강의</h2>
            <GreenBox>
              <Img>
                <img src=" https://i1.ytimg.com/vi/GNDugFKBqd0/mqdefault.jpg" />
              </Img>
              <Content>
                <Title>무한 질럿</Title>
                <Gamer>임요환</Gamer>
              </Content>
            </GreenBox>
            <GreenBox>
              <Img>
                <img src=" https://i1.ytimg.com/vi/yYeDIWGZnwk/mqdefault.jpg" />
              </Img>

              <Content>
                <Title>무한 질럿</Title>
                <Gamer>임요환</Gamer>
              </Content>
            </GreenBox>
            <GreenBox>
              <Img>
                <img src=" https://i1.ytimg.com/vi/3ROYmns-F9E/mqdefault.jpg" />
              </Img>
              <Content>
                <Title>리콜 되면 알지?</Title>
                <Gamer>임요환</Gamer>
              </Content>
            </GreenBox>
          </RedBox>
        </BrownBox>
      </StarBackground>
      <StarBackground>
        <Effect />
        <BrownBox>
          <RedBox>
            <h2>신규 강의</h2>
            <GreenBox>
              <Img>
                <img src=" https://i1.ytimg.com/vi/GNDugFKBqd0/mqdefault.jpg" />
              </Img>
              <Content>
                <Title>무한 질럿</Title>
                <Gamer>임요환</Gamer>
              </Content>
            </GreenBox>
            <GreenBox>
              <Img>
                <img src=" https://i1.ytimg.com/vi/yYeDIWGZnwk/mqdefault.jpg" />
              </Img>

              <Content>
                <Title>무한 질럿</Title>
                <Gamer>임요환</Gamer>
              </Content>
            </GreenBox>
            <GreenBox>
              <Img>
                <img src=" https://i1.ytimg.com/vi/3ROYmns-F9E/mqdefault.jpg" />
              </Img>
              <Content>
                <Title>리콜 되면 알지?</Title>
                <Gamer>임요환</Gamer>
              </Content>
            </GreenBox>
          </RedBox>
        </BrownBox>
      </StarBackground>
      <StarBackground>
        <Effect />
        <BrownBox>
          <RedBox>
            <h2>신규 강의</h2>
            <GreenBox>
              <Img>
                <img src=" https://i1.ytimg.com/vi/GNDugFKBqd0/mqdefault.jpg" />
              </Img>
              <Content>
                <Title>무한 질럿</Title>
                <Gamer>임요환</Gamer>
              </Content>
            </GreenBox>
            <GreenBox>
              <Img>
                <img src=" https://i1.ytimg.com/vi/yYeDIWGZnwk/mqdefault.jpg" />
              </Img>

              <Content>
                <Title>무한 질럿</Title>
                <Gamer>임요환</Gamer>
              </Content>
            </GreenBox>
            <GreenBox>
              <Img>
                <img src=" https://i1.ytimg.com/vi/3ROYmns-F9E/mqdefault.jpg" />
              </Img>
              <Content>
                <Title>리콜 되면 알지?</Title>
                <Gamer>임요환</Gamer>
              </Content>
            </GreenBox>
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
`;
const StarBackground = styled.div`
  width: 100%;
  padding: 50px 0;
  background-image: url("https://static.starcraft.com/production/images/site/backdrops/backdrop-stars.890c5929ec65159852db3a0fab438e7aaa5c210f.jpg");
`;

const BrownBox = styled.div`
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
  padding: 20px 70px;

  h2 {
    width: 100%;
    color: #b8bbcc;
    font-size: 24px;
    margin-bottom: 30px;
  }
`;

const GreenBox = styled.div`
  width: 320px;
  border: 1px solid rgba(0, 204, 0, 0.6);
  box-shadow: 0 0 20px rgb(0 204 0 / 50%), inset 0 0 0 1px #000,
    inset 0 0 0 2px rgb(0 204 0 / 50%);

  &:hover {
    border-color: #ccff66;
    box-shadow: 0 0 10px #ccff66, inset 0 0 0 1px #000, inset 0 0 0 2px #ccff66;
    color: #ccff66;
  }
`;

const Img = styled.div`
  width: 100%;
  height: 180px;
  border-bottom: 2px solid rgba(0, 204, 0, 0.6);
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