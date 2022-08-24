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
    thumbnailUrl: "",
    comment: "",
    level: "",
    race: "",
    price: "",
  });
  const [count, setCount] = React.useState(0); //아이템 총 개수
  const [currentpage, setCurrentpage] = React.useState(1); //현재페이지
  const [postPerPage] = React.useState(10); //페이지당 아이템 개수
  const [indexOfLastPost, setIndexOfLastPost] = React.useState(0);
  const [indexOfFirstPost, setIndexOfFirstPost] = React.useState(0);
  const [currentPosts, setCurrentPosts] = React.useState(0);

  useEffect(() => {
    axios
      .get("/admin-management/courses")
      .then((response) => setLectures(...lectures, response.data.data));
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
      gamerId: inputs.gamerId,
      title: inputs.title,
      videoUrl: inputs.videoUrl,
      thumbnailUrl: inputs.thumbnailUrl,
      comment: inputs.comment,
      level: inputs.level,
      race: inputs.race,
      price: inputs.price,
    };
    setLectures([...lectures, lecture]);
    setInputs({
      id: lectures.length,
      gamerId: "",
      title: "",
      videoUrl: "",
      thumbnailUrl: "",
      comment: "",
      level: "",
      race: 0,
      price: "",
    });
    axios.post("/admin-management/course", {
      gamerId: inputs.gamerId,
      title: inputs.title,
      videoUrl: inputs.videoUrl,
      thumbnailUrl: inputs.thumbnailUrl,
      comment: inputs.comment,
      level: inputs.level,
      race: inputs.race,
      price: inputs.price,
    });
  }
  console.log(lectures);

  function updateHandler(id) {
    setLectures(
      lectures.map((lecture) =>
        lecture.id === id
          ? {
              id: id,
              comment: inputs.comment,
              gamerName: inputs.gamerName,
              level: inputs.level,
              price: inputs.price,
              race: inputs.race,
              thumbnailUrl: inputs.thumbnailUrl,
              title: inputs.title,
              videoUrl: inputs.videoUrl,
            }
          : lecture
      )
    );
    setInputs({
      id: lectures.lenght + 1,
      gamerId: "",
      comment: "",
      gamerName: "",
      level: "",
      price: 0,
      race: "",
      thumbnailUrl: "",
      title: "",
      videoUrl: "",
    });

    axios.put(`/admin-management/course/${id}`, {
      gamerId: inputs.gamerId,
      title: inputs.title,
      videoUrl: inputs.videoUrl,
      thumblink: inputs.thumbnailUrl,
      comment: inputs.comment,
      level: inputs.level,
      race: inputs.race,
      price: inputs.price,
    });
  }
  return (
    <List>
      <ListHeader></ListHeader>
      {currentPosts && lectures.length > 0 ? (
        currentPosts.map((list) => (
          <fieldset key={list.id}>
            <ListWrap>
              <Left>
                <div>
                  id: <span>{list.id}</span>
                </div>
                <div>
                  title: <span>{list.title}</span>
                </div>
                <div>
                  videoUrl :<span>{list.videoUrl}</span>
                </div>
                <div>
                  thumbnailUrl : <span>{list.thumbnailUrl}</span>
                </div>
                <div>
                  comment : <span>{list.comment}</span>
                </div>
                <div>
                  level : <span>{list.level}</span>
                </div>
                <div>
                  race : <span>{list.race}</span>
                </div>
                <div>
                  price : <span>{list.price}</span>
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
                    placeholder="gamerId"
                    name="gamerId"
                    onChange={changeHandler}
                  />
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
                    placeholder="thumbnailUrl"
                    name="thumbnailUrl"
                    onChange={changeHandler}
                  />
                  <input
                    type="text"
                    placeholder="comment"
                    name="comment"
                    onChange={changeHandler}
                  />{" "}
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
                  />
                  <input
                    type="text"
                    placeholder="price"
                    name="price"
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

      <CreateWrap>
        <form onSubmit={submitHandler}>
          <input
            type="text"
            placeholder="gamerId"
            name="gamerId"
            onChange={changeHandler}
          />
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
            placeholder="thumbnailUrl"
            name="thumbnailUrl"
            onChange={changeHandler}
          />
          <input
            type="text"
            placeholder="comment"
            name="comment"
            onChange={changeHandler}
          />{" "}
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
          />
          <input
            type="text"
            placeholder="price"
            name="price"
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

const Center = styled.div`
  width: 100%;

  svg {
    margin: 0 auto;
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

const CreateWrap = styled.div`
  margin-top: 30px;

  form {
    width: 200px;
    display: flex;
    flex-direction: column;
  }
`;
const UpdateWrap = styled.div`
  margin-left: auto;
  margin-right: 50px;
  form {
    width: 200px;
    display: flex;
    flex-direction: column;
  }

  margin-bottom: 20px;
`;
