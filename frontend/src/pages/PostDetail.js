import React, { useEffect, useState } from "react";
import styled from "styled-components";
import axios from "axios";
import { useParams } from "react-router-dom";
import { ThreeDots } from "react-loader-spinner";
import { FaUserAlt } from "react-icons/fa";
import { useSelector } from "react-redux";
import { RiDeleteBin5Line } from "react-icons/ri";
import { BsPencil } from "react-icons/bs";
import FixEditor from "../components/FixEditor";
export default function FreeContent() {
  const [post, setPost] = useState([]);
  const { id } = useParams();
  const email = useSelector((state) => state.userinfo.value.email);
  const header = useSelector((state) => state.userinfo.value.header);
  const [amend, setAmend] = useState(false);
  const [content, setContent] = useState("");

  const handleText = (value) => {
    setContent(value);
    console.log(content);
  };

  useEffect(() => {
    axios
      .get(`https://www.dokuny.blog/posts/${id}`)
      .then((res) => setPost(res.data.data));
  }, []);

  function toggle() {
    setAmend(!amend);
  }

  function timeEdit(time) {
    time = Array.from(time);
    time = Array.from(time);
    time[time.indexOf("T")] = "/";
    time = time.slice(0, time.lastIndexOf(":"));
    return time.join("");
  }

  function deletePost(id) {
    axios
      .delete(`https://www.dokuny.blog/posts/${id}`, {
        headers: header,
      })
      .then((res) => {
        console.log(res);
        document.location.href = "/post";
      });
  }

  return (
    <Main>
      <Effect />
      <Wrap>
        {amend ? (
          <RedBox>
            <FixEditor
              writing={handleText}
              content={post.content}
              title={post.title}
              boardKind={post.boardKind}
              id={id}
            />
          </RedBox>
        ) : (
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
                  {email === post.member.email ? (
                    <div onClick={() => deletePost(id)}>
                      <RiDeleteBin5Line />
                    </div>
                  ) : null}
                  <h1>{post.title}</h1>
                  {email === post.member.email ? (
                    <div onClick={toggle}>
                      <BsPencil />
                    </div>
                  ) : null}
                </Title>
                <Date>
                  <p>{timeEdit(post.member.createdDate)}</p>
                  <span>
                    <FaUserAlt /> {post.member.email}
                  </span>
                </Date>
                <Content>
                  <div dangerouslySetInnerHTML={{ __html: post.content }} />
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
        )}
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
  @media screen and (max-width: 666px) {
    width: 300px;
  }
  @media screen and (max-width: 300px) {
    display: none;
  }
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
  @media screen and (max-width: 1024px) {
    width: 90%;
  }
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
  padding: 20px 20px;

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
  display: flex;
  h1 {
    width: 100%;
    text-align: center;
    font-size: 30px;

    @media screen and (max-width: 666px) {
      font-size: 24px;
    }
  }

  svg {
    font-size: 26px;
    color: #80ff66;

    &:hover {
      color: #ccff66;
    }
  }
  @media screen and (max-width: 666px) {
    font-size: 22px;
  }
`;

const Date = styled.div`
  margin-top: 20px;
  p {
    float: right;
    @media screen and (max-width: 500px) {
      display: none;
    }
  }
  svg {
    font-size: 14px;
    @media screen and (max-width: 666px) {
      font-size: 11px;
    }
    @media screen and (max-width: 500px) {
      font-size: 9px;
    }
  }

  @media screen and (max-width: 666px) {
    font-size: 11px;
  }
`;

const Content = styled.div`
  margin-top: 30px;
  padding-bottom: 100px;
  p {
    font-size: 18px;
    display: flex;
    flex-direction: column;
  }

  img {
    max-width: 50%;
    max-height: 50%;
    margin: 0 auto;
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
