import React, { useEffect, useState } from "react";
import styled from "styled-components";
import axios from "axios";
import { Link } from "react-router-dom";
import { useParams } from "react-router-dom";
import { ThreeDots } from "react-loader-spinner";
import { FaUserAlt } from "react-icons/fa";

export default function FreeContent() {
  const [post, setPost] = useState([]);
  const { id } = useParams();

  useEffect(() => {
    axios
      .get(`https://www.dokuny.blog/posts/${id}`)
      .then((res) => setPost(res.data.data));
  }, []);

  console.log(post);

  function timeEdit(time) {
    time = Array.from(time);
    time = Array.from(time);
    time[time.indexOf("T")] = "/";
    time = time.slice(0, time.lastIndexOf(":"));
    return time.join("");
  }
  return (
    <Main>
      <Effect />
      <Wrap>
        <RedBox>
          {post.length === 0 ? (
            <Center>
              <ThreeDots
                color="#ccff66"
                margin="0 auto"
                height={80}
                width={100}
              />
            </Center>
          ) : (
            <PostArea>
              <Title>
                <h1>{post.title}</h1>
              </Title>
              <Date>
                <p>{timeEdit(post.member.createdDate)}</p>
                <span>
                  <FaUserAlt /> {post.member.email}
                </span>
              </Date>
              <Content>
                <p>{post.content}</p>
              </Content>
            </PostArea>
          )}

          <Bottom>
            <div
              onClick={() => {
                document.location.href = "/post";
              }}
            >
              뒤로가기
            </div>
          </Bottom>
        </RedBox>
      </Wrap>
    </Main>
  );
}
const Main = styled.main`
  width: 100%;
  padding-top: 70px;
  min-height: 900px;
  color: #b8bbcc;
  background-image: url("https://static.starcraft.com/production/images/site/backdrops/backdrop-stars.890c5929ec65159852db3a0fab438e7aaa5c210f.jpg");
`;
const Effect = styled.div`
  width: 593px;
  height: 53px;
  margin: 0 auto;
  background-image: url("https://static.starcraft.com/production/images/site/dividers/divider-terminal-detail.3a193b6d6e3a7d62cee253b2a245bbdd73bea9b6.png");
`;

const Wrap = styled.div`
  width: 60%;
  margin: 0 auto;
  padding: 5px;
  background-color: rgba(0, 0, 0, 0.8);
  border: 2px solid #331f1f;
  border-radius: 6px;
  margin: 0 auto;
  color: #b8bbcc;
`;

const RedBox = styled.div`
  margin: 0 auto;
  min-height: 300px;
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

const Center = styled.div`
  width: 100%;
  margin-top: 50px;
  svg {
    margin: 0 auto;
  }
`;
const PostArea = styled.div`
  width: 100%;
  min-height: 300px;
`;
const Title = styled.div`
  width: 100%;
  h1 {
    text-align: center;
    font-size: 30px;
  }
`;

const Date = styled.div`
  p {
    float: right;
  }
  svg {
    font-size: 14px;
  }
`;

const Content = styled.div`
  margin-top: 30px;
  p {
    font-size: 18px;
  }
`;

const Bottom = styled.div`
  width: 100%;
  display: flex;

  div {
    cursor: pointer;
    color: #00cc00;

    &:hover {
      color: #ddff99;
    }
  }
`;
