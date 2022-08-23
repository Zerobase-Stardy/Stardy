import React, { useEffect, useState } from "react";
import styled from "styled-components";
import axios from "axios";
import Page from "../components/Page";
import { ThreeDots } from "react-loader-spinner";
import { Link } from "react-router-dom";

export default function AdminMember() {
  const [members, setMembers] = useState([]);
  const [count, setCount] = React.useState(0); //아이템 총 개수
  const [currentpage, setCurrentpage] = React.useState(1); //현재페이지
  const [postPerPage] = React.useState(10); //페이지당 아이템 개수
  const [indexOfLastPost, setIndexOfLastPost] = React.useState(0);
  const [indexOfFirstPost, setIndexOfFirstPost] = React.useState(0);
  const [currentPosts, setCurrentPosts] = React.useState(0);

  useEffect(() => {
    axios
      .get("https://jsonplaceholder.typicode.com/users")
      .then((res) => setMembers(res.data));
  }, []);

  useEffect(() => {
    setCount(members.length);
    setIndexOfLastPost(currentpage * postPerPage);
    setIndexOfFirstPost(indexOfLastPost - postPerPage);
    setCurrentPosts(members.slice(indexOfFirstPost, indexOfLastPost));
  }, [currentpage, indexOfFirstPost, indexOfLastPost, members, postPerPage]);

  const setPage = (e) => {
    setCurrentpage(e);
  };

  function deleteHandler(id) {
    setMembers(members.filter((e) => e.id !== id));
  }

  return (
    <List>
      <ListHeader>
        <div>ID</div>
        <div>Name</div>
        <div></div>
      </ListHeader>
      {currentPosts && members.length > 0 ? (
        currentPosts.map((list) => (
          <ListBody key={list.id}>
            <div>{list.id}</div>
            <div>{list.name}</div>
            <DeleteBtn
              type="button"
              value="삭제"
              onClick={() => deleteHandler(list.id)}
            />
          </ListBody>
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
`;
const ListHeader = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-between;
  height: 40px;
  background-color: black;
  color: white;
  div {
    font-size: 30px;
    width: 25%;
  }
`;

const ListBody = styled.div`
  width: 100%;
  margin-bottom: 10px;
  display: flex;
  justify-content: space-between;
  height: 30px;
  font-size: 18px;
  div,
  input {
    width: 25%;
  }

  &:hover {
    background-color: yellow;
  }
`;
const Center = styled.div`
  width: 100%;

  svg {
    margin: 0 auto;
  }
`;

const DeleteBtn = styled.input`
  width: 20px;
`;

const CreateBtn = styled.input`
  width: 200px;
  height: 30px;
`;

const CreateWrap = styled.div`
  margin-top: 30px;

  form {
    width: 200px;
    display: flex;
    flex-direction: column;
  }
`;
