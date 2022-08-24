import React, { useEffect, useState } from "react";
import styled from "styled-components";
import axios from "axios";
import Page from "../components/Page";
import { ThreeDots } from "react-loader-spinner";

export default function AdminLecture() {
  const [lectures, setLectures] = useState([]);
  const [inputs, setInputs] = useState({
    gamerId: "",
    title: "",
    videoUrl: "",
    thumblink: "",
    comment: "",
    level: "",
    race: "",
    Long: 0,
  });
  const [count, setCount] = React.useState(0); //아이템 총 개수
  const [currentpage, setCurrentpage] = React.useState(1); //현재페이지
  const [postPerPage] = React.useState(10); //페이지당 아이템 개수
  const [indexOfLastPost, setIndexOfLastPost] = React.useState(0);
  const [indexOfFirstPost, setIndexOfFirstPost] = React.useState(0);
  const [currentPosts, setCurrentPosts] = React.useState(0);
  useEffect(() => {
    axios
      .get("https://jsonplaceholder.typicode.com/photos")
      .then((res) => setLectures(res.data));
  }, []);

  useEffect(() => {
    setCount(lectures.length);
    setIndexOfLastPost(currentpage * postPerPage);
    setIndexOfFirstPost(indexOfLastPost - postPerPage);
    setCurrentPosts(lectures.slice(indexOfFirstPost, indexOfLastPost));
  }, [currentpage, indexOfFirstPost, indexOfLastPost, lectures, postPerPage]);

  const setPage = (e) => {
    setCurrentpage(e);
  };

  function deleteHandler(id) {
    setLectures(lectures.filter((e) => e.id !== id));
  }

  //텍스트 변경 핸들러
  function changeHandler(e) {
    setInputs({
      ...inputs,
      [e.target.name]: e.target.value,
    });
  }

  //제출 핸들러
  function submitHandler(e) {
    e.preventDefault();
    const lecture = {
      id: lectures.length + 1,
      title: inputs.title,
      videoUrl: inputs.videoUrl,
      thumblink: inputs.thumblink,
      comment: inputs.comment,
      level: inputs.level,
      race: inputs.race,
      Long: 0,
    };
    setLectures([...lectures, lecture]);
    setInputs({
      id: lectures.lenght + 1,
      title: "",
      videoUrl: "",
      thumblink: "",
      comment: "",
      level: "",
      race: "",
      Long: 0,
    });
  }

  function updateHandler(id) {
    setLectures(
      lectures.map((lecture) =>
        lecture.id === id
          ? {
              ...lecture,
              gamerId: id,
              title: inputs.title,
              videoUrl: inputs.videoUrl,
              thumblink: inputs.thumblink,
              comment: inputs.comment,
              level: inputs.level,
              race: inputs.race,
              Long: 0,
            }
          : lecture
      )
    );
    setInputs({
      id: lectures.lenght + 1,
      title: "",
      videoUrl: "",
      thumblink: "",
      comment: "",
      level: "",
      race: "",
      Long: 0,
    });
  }
  return (
    <List>
      <ListHeader>
        <div>Id</div>
        <div>Thumblink</div>
        <div>Title</div>
        <div></div>
      </ListHeader>
      {currentPosts && lectures.length > 0 ? (
        currentPosts.map((list) => (
          <fieldset key={list.id}>
            <ListBody>
              <div>{list.id}</div>
              <div>
                <img src={list.thumblink} alt="thumbnail" />
              </div>
              <div>{list.title}</div>
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
                  placeholder="title"
                  name="title"
                  onChange={changeHandler}
                />
                <input
                  type="text"
                  placeholder="videoUrl"
                  name="videoUrl"
                  onChange={changeHandler}
                />
                <input
                  type="text"
                  placeholder="thumblink"
                  name="thumblink"
                  onChange={changeHandler}
                />
                <input
                  type="text"
                  placeholder="comment"
                  name="comment"
                  onChange={changeHandler}
                />
                <input
                  type="text"
                  placeholder="level"
                  name="level"
                  onChange={changeHandler}
                />{" "}
                <input
                  type="text"
                  placeholder="race"
                  name="race"
                  onChange={changeHandler}
                />{" "}
                <input
                  type="text"
                  placeholder="Long"
                  name="Long"
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

      <CreateWrap>
        <form onSubmit={submitHandler}>
          <input
            type="text"
            placeholder="title"
            name="title"
            onChange={changeHandler}
          />
          <input
            type="text"
            placeholder="videoUrl"
            name="videoUrl"
            onChange={changeHandler}
          />
          <input
            type="text"
            placeholder="thumblink"
            name="thumblink"
            onChange={changeHandler}
          />
          <input
            type="text"
            placeholder="comment"
            name="comment"
            onChange={changeHandler}
          />
          <input
            type="text"
            placeholder="level"
            name="level"
            onChange={changeHandler}
          />{" "}
          <input
            type="text"
            placeholder="race"
            name="race"
            onChange={changeHandler}
          />{" "}
          <input
            type="text"
            placeholder="Long"
            name="Long"
            onChange={changeHandler}
          />
          <CreateBtn type="submit" value="등록" />
        </form>
      </CreateWrap>
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
    width: 25%;
  }
`;

const ListBody = styled.div`
  width: 100%;
  height: 150px;
  margin-bottom: 10px;
  display: flex;
  justify-content: space-between;
  font-size: 18px;

  &:hover {
    background-color: yellow;
  }

  div,
  input {
    width: 25%;
  }
`;

const Center = styled.div`
  width: 100%;

  svg {
    margin: 0 auto;
  }
`;

const DeleteBtn = styled.input``;

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
const UpdateWrap = styled.div`
  form {
    width: 200px;
    display: flex;
    flex-direction: column;
  }

  margin-bottom: 20px;
`;
