import React, { useEffect, useState } from "react";
import styled from "styled-components";
import axios from "axios";
import Page from "../components/Page";
import { ThreeDots } from "react-loader-spinner";
import cookies from "react-cookies";

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

  const accessToken = cookies.load("accessToken");
  const header = {
    Authorization: `Bearer ${accessToken}`,
  };

  useEffect(() => {
    axios
      .get("https://www.dokuny.blog/admin-management/gamers", {
        headers: header,
      })
      .then((response) => {
        setGamers(...gamers, response.data.data);
      })
      .catch((error) => {
        console.log(error);
      });
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

  //axios Delete

  function deleteHandler(id) {
    setGamers(gamers.filter((e) => e.id !== id));

    axios.delete(
      `https://www.dokuny.blog/admin-management/gamers/${id}`,

      {
        headers: header,
      }
    );
  }

  //Input 핸들러
  function changeHandler(e) {
    setInputs({
      ...inputs,
      [e.target.name]: e.target.value,
    });
  }

  //axios Post 핸들러  put gamers 넘겨주면 됨
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
    axios
      .post(
        "https://www.dokuny.blog/admin-management/gamers",
        {
          name: inputs.name,
          introduce: inputs.introduce,
          nickname: inputs.nickName,
          race: inputs.race,
        },
        {
          headers: header,
        }
      )
      .then(function (response) {})
      .catch(function (error) {
        console.log(error);
      });
  }

  //axios Put
  function updateHandler(id) {
    setGamers(
      gamers.map((gamer) =>
        gamer.id === id
          ? {
              ...gamer,
              id: id,
              introduce: inputs.introduce,
              name: inputs.name,
              nickName: inputs.nickName,
              race: inputs.race,
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
    axios
      .put(
        `https://www.dokuny.blog/admin-management/gamers/${id}`,
        {
          introduce: inputs.introduce,
          name: inputs.name,
          nickname: inputs.nickName,
          race: inputs.race,
        },
        {
          headers: header,
        }
      )
      .then(function (response) {
        console.log(response);
      })
      .catch(function (error) {
        console.log(error);
      });
  }

  return (
    <List>
      <ListHeader></ListHeader>
      {currentPosts && gamers.length > 0 ? (
        currentPosts.map((list) => (
          <fieldset key={list.id}>
            <ListWrap>
              <Left>
                <div>
                  id : <span>{list.id}</span>
                </div>
                <div>
                  introduce : <span>{list.introduce}</span>
                </div>
                <div>
                  name : <span>{list.name}</span>
                </div>
                <div>
                  nickname : <span>{list.nickname}</span>
                </div>
                <div>
                  race : <span>{list.race}</span>
                </div>
              </Left>

              <UpdateWrap>
                <form
                  onSubmit={(e) => {
                    e.preventDefault();
                    updateHandler(list.id);
                  }}
                >
                  <input
                    type="text"
                    placeholder="introduce"
                    name="introduce"
                    onChange={changeHandler}
                  />
                  <input
                    type="text"
                    placeholder="name"
                    name="name"
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
                    placeholder="race"
                    name="race"
                    onChange={changeHandler}
                  />
                  <CreateBtn type="submit" value="수정" />
                </form>
              </UpdateWrap>
              <DeleteBtn
                type="button"
                value="삭제"
                onClick={() => deleteHandler(list.id)}
              />
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

      <FormZone>
        <CreateWrap>
          <form onSubmit={submitHandler}>
            <input
              type="text"
              placeholder="introduce"
              name="introduce"
              onChange={changeHandler}
            />
            <input
              type="text"
              placeholder="name"
              name="name"
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
              placeholder="race"
              name="race"
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

const DeleteBtn = styled.input`
  margin-left: auto;
  width: 90px;
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
  margin-right: 50px;

  margin-bottom: 20px;
`;
const Center = styled.div`
  width: 100%;

  svg {
    margin: 0 auto;
  }
`;
