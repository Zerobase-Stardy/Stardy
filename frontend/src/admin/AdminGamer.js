import React, { useEffect, useState } from "react";
import styled from "styled-components";
import axios from "axios";
import Page from "../components/Page";
import { ThreeDots } from "react-loader-spinner";
import { Link } from "react-router-dom";

export default function AdminGamer() {
  const [gamers, setGamers] = useState([]);
  const [count, setCount] = React.useState(0); //아이템 총 개수
  const [currentpage, setCurrentpage] = React.useState(1); //현재페이지
  const [postPerPage] = React.useState(10); //페이지당 아이템 개수
  const [indexOfLastPost, setIndexOfLastPost] = React.useState(0);
  const [indexOfFirstPost, setIndexOfFirstPost] = React.useState(0);
  const [currentPosts, setCurrentPosts] = React.useState(0);

  const [inputs, setInputs] = useState({
    name: "",
    race: "",
    nickName: "",
    introduce: "",
  });
  useEffect(() => {
    axios
      .get("https://jsonplaceholder.typicode.com/users")
      .then((res) => setGamers(res.data));
  }, []);

  useEffect(() => {
    setCount(gamers.length);
    setIndexOfLastPost(currentpage * postPerPage);
    setIndexOfFirstPost(indexOfLastPost - postPerPage);
    setCurrentPosts(gamers.slice(indexOfFirstPost, indexOfLastPost));
  }, [currentpage, indexOfFirstPost, indexOfLastPost, gamers, postPerPage]);

  const setPage = (e) => {
    setCurrentpage(e);
  };

  function deleteHandler(id) {
    setGamers(gamers.filter((e) => e.id !== id));
  }

  //Input 핸들러
  function changeHandler(e) {
    setInputs({
      ...inputs,
      [e.target.name]: e.target.value,
    });
  }

  //제출 핸들러
  function submitHandler(e) {
    e.preventDefault();
    const gamer = {
      id: gamers.length + 1,
      name: inputs.name,
      race: inputs.race,
      nickName: inputs.nickName,
      introduce: inputs.introduce,
    };
    setGamers([...gamers, gamer]);
    setInputs({
      id: gamers.lenght + 1,
      name: "",
      race: "",
      nickName: "",
      introduce: "",
    });
  }

  function updateHandler(id) {
    setGamers(
      gamers.map((gamer) =>
        gamer.id === id
          ? {
              ...gamer,
              id: id,
              name: inputs.name,
              race: inputs.race,
              nickName: inputs.nickName,
              introduce: inputs.introduce,
            }
          : gamer
      )
    );
    setInputs({
      id: gamers.lenght + 1,
      name: "",
      race: "",
      nickName: "",
      introduce: "",
    });
  }

  return (
    <List>
      <ListHeader>
        <div>ID</div>
        <div>Name</div>
        <div></div>
      </ListHeader>
      {currentPosts && gamers.length > 0 ? (
        currentPosts.map((list) => (
          <fieldset key={list.id}>
            <ListBody>
              <div>{list.id}</div>
              <div>{list.name}</div>
              <DeleteBtn
                type="button"
                value="삭제"
                onClick={() => deleteHandler(list.id)}
              />
            </ListBody>

            <UpdateWrap>
              <form
                onSubmit={(e) => {
                  e.preventDefault();
                  updateHandler(list.id);
                }}
              >
                <input
                  type="text"
                  placeholder="name"
                  name="name"
                  onChange={changeHandler}
                />
                <input
                  type="text"
                  placeholder="race"
                  name="race"
                  onChange={changeHandler}
                />
                <input
                  type="text"
                  placeholder="nickName"
                  name="nickName"
                  onChange={changeHandler}
                />
                <input
                  type="text"
                  placeholder="introduce"
                  name="introduce"
                  onChange={changeHandler}
                />
                <CreateBtn type="submit" value="수정" />
              </form>
            </UpdateWrap>
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

      <FormZone>
        <CreateWrap>
          <form onSubmit={submitHandler}>
            <input
              type="text"
              placeholder="name"
              name="name"
              onChange={changeHandler}
            />
            <input
              type="text"
              placeholder="race"
              name="race"
              onChange={changeHandler}
            />
            <input
              type="text"
              placeholder="nickName"
              name="nickName"
              onChange={changeHandler}
            />
            <input
              type="text"
              placeholder="introduce"
              name="introduce"
              onChange={changeHandler}
            />
            <CreateBtn type="submit" value="등록" />
          </form>
        </CreateWrap>
      </FormZone>
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
const FormZone = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-between;
`;

const CreateWrap = styled.div`
  margin-top: 30px;

  form {
    width: 200px;
    display: flex;
    flex-direction: column;
  }
`;

const UpdateWrap = styled.div`
  form {
    width: 200px;
    display: flex;
    flex-direction: column;
  }

  margin-bottom: 20px;
`;
