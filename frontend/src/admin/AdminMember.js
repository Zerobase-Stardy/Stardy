import React, { useEffect, useState } from "react";
import styled from "styled-components";
import axios from "axios";
import Page from "../components/Page";
import { ThreeDots } from "react-loader-spinner";
import cookies from "react-cookies";

export default function AdminMember() {
  const [members, setMembers] = useState([]);
  const [count, setCount] = React.useState(0); //아이템 총 개수
  const [currentpage, setCurrentpage] = React.useState(1); //현재페이지
  const [postPerPage] = React.useState(10); //페이지당 아이템 개수
  const [indexOfLastPost, setIndexOfLastPost] = React.useState(0);
  const [indexOfFirstPost, setIndexOfFirstPost] = React.useState(0);
  const [currentPosts, setCurrentPosts] = React.useState(0);

  const [nickname, setNickname] = useState("");

  const accessToken = cookies.load("accessToken");
  const header = {
    Authorization: `Bearer ${accessToken}`,
  };
  useEffect(() => {
    axios
      .get("https://www.dokuny.blog/admin-management/members", {
        headers: header,
      })
      .then((res) => setMembers(res.data.data.content))
      .catch((err) => console.log(err));
  }, []);

  function changeHandler(e) {
    setNickname(e.target.value);
  }

  function updateHandler(id) {
    axios
      .patch(
        `https://www.dokuny.blog/admin-management/members/nickname/${id}`,
        {
          nickname: nickname,
        },
        {
          headers: header,
        }
      )
      .then((res) => console.log(res))
      .catch((err) => console.log(`에러 : ${err}`));
  }

  useEffect(() => {
    setCount(members.length);
    setIndexOfLastPost(currentpage * postPerPage);
    setIndexOfFirstPost(indexOfLastPost - postPerPage);
    setCurrentPosts(members.slice(indexOfFirstPost, indexOfLastPost));
  }, [currentpage, indexOfFirstPost, indexOfLastPost, members, postPerPage]);

  const setPage = (e) => {
    setCurrentpage(e);
  };

  return (
    <List>
      <ListHeader></ListHeader>
      {currentPosts && members.length > 0 ? (
        currentPosts.map((list) => (
          <fieldset key={list.id}>
            <ListWrap>
              <Left>
                <div>
                  id : <span>{list.id}</span>
                </div>
                <div>
                  email : <span>{list.email}</span>
                </div>
                <div>
                  nickname : <span>{list.nickname}</span>
                </div>
                <div>
                  point : <span>{list.point}</span>
                </div>
              </Left>

              <UpdateWrap>
                <form
                  onSubmit={(e) => {
                    updateHandler(list.id);
                  }}
                >
                  <input
                    type="text"
                    placeholder="nickname"
                    name="nickname"
                    onChange={changeHandler}
                  />
                  <CreateBtn type="submit" value="수정" />
                </form>
              </UpdateWrap>
            </ListWrap>
          </fieldset>
        ))
      ) : (
        <Center>
          <ThreeDots
            color="#87c3a1"
            text-align="center"
            height={80}
            width={80}
          />
        </Center>
      )}
      <Page page={currentpage} count={count} setPage={setPage} />
    </List>
  );
}

const List = styled.div`
  margin-top: 50px;
  display: grid;

  fieldset {
    border: 1px solid black;

    &:hover {
      background-color: yellow;
    }
  }
`;
const ListHeader = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-between;
  height: 40px;
  background-color: black;
  color: white;
`;

const ListWrap = styled.div`
  width: 100%;
  margin-bottom: 10px;
  display: flex;
  font-size: 18px;

  &:hover {
    background-color: yellow;
  }
`;

const Left = styled.div`
  width: 100%;
  display: flex;

  gap: 5px;
  flex-direction: column;

  span {
    font-weight: bold;
    color: #fe4040;
  }
`;

const CreateBtn = styled.input`
  width: 200px;
  height: 30px;
`;

const UpdateWrap = styled.div`
  form {
    width: 200px;
    display: flex;
    flex-direction: column;
  }
  margin-right: 50px;

  margin-bottom: 20px;
`;
const Center = styled.div`
  width: 100%;

  svg {
    margin: 0 auto;
  }
`;
