import React, { useState, useRef, useMemo } from "react";
import ReactQuill, { Quill } from "react-quill";
import { CustomToolbar } from "../Layout/CustomToolbar";
import axios from "axios";
import ImageResize from "quill-image-resize-module-react";
import "react-quill/dist/quill.snow.css";
import styled from "styled-components";
import { useSelector } from "react-redux";

Quill.register("modules/imageResize", ImageResize);

export default function Editor(props) {
  const quillRef = useRef();

  const imageHandler = () => {
    const input = document.createElement("input");
    input.setAttribute("type", "file");
    input.setAttribute("accept", "image/*");
    input.click();

    input.onchange = () => {
      const formData = new FormData();
      const file = input.files[0];
      formData.append("image", file);
      axios.post("https://dokuny.blog/postImage", formData).then((res) => {
        const url = res.data.data.path;
        const editor = quillRef.current.getEditor();
        const range = editor.getSelection();
        editor.insertEmbed(range.index, "image", url);
      });
    };
  };

  const modules = useMemo(() => {
    return {
      toolbar: {
        container: "#toolbar",
        handlers: {
          image: imageHandler,
        },
      },
      imageResize: {
        parchment: Quill.import("parchment"),
        modules: ["Resize", "DisplaySize", "Toolbar"],
      },
    };
  }, []);

  const formats = [
    "header",
    "font",
    "size",
    "bold",
    "italic",
    "underline",
    "list",
    "bullet",
    "align",
    "color",
    "background",
    "image",
  ];
  const header = useSelector((state) => state.userinfo.value.header);
  const [content, setText] = useState(props.content);
  const [title, setTitle] = useState(props.title);
  const [boardKind, setBoardKind] = useState(props.boardKind);

  function handleText(value) {
    setText(value);
    props.writing(content);
  }

  return (
    <Wrap>
      <Top>
        <h2>글수정</h2>
        <form>
          <label>제목</label>
          <Title
            type="text"
            placeholder="제목"
            value={title}
            onChange={(e) => {
              setTitle(e.target.value);
            }}
          />
        </form>
      </Top>
      <div className="text-editor">
        <CustomToolbar />
        <ReactQuill
          ref={quillRef}
          modules={modules}
          formats={formats}
          placeholder="ㅇㅇ"
          value={content}
          onChange={handleText}
        />
      </div>
      <Bottom>
        <SubmitBtn
          type="submit"
          value="제출"
          onClick={() => {
            if (title !== "" && boardKind !== "" && content !== "") {
              console.log({
                title: title,
                boardKind: boardKind,
                content: content,
              });
              axios
                .put(
                  `https://www.dokuny.blog/posts/${props.id}`,
                  {
                    title: title,
                    boardKind: boardKind,
                    content: content,
                    postId: Number(props.id),
                  },

                  {
                    headers: header,
                  },
                  console.log({
                    postId: Number(props.id),
                    title: title,
                    boardKind: boardKind,
                    content: content,
                  })
                )
                .then((res) => {
                  document.location.href = "/post";
                })
                .catch((err) => alert(err));
            } else {
              if (title === "") {
                alert("제목을 입력해주세요");
              } else if (content === "") {
                alert("내용을 입력해주세요");
              } else if (boardKind === "") {
                alert("채널을 선택해 주세요");
              }
            }
          }}
        ></SubmitBtn>
        <CancelBtn
          type="button"
          value="취소"
          onClick={() => {
            document.location.href = "/post";
          }}
        />
      </Bottom>
    </Wrap>
  );
}

const Wrap = styled.div`
  width: 100%;
  #toolbar {
    border: 1px solid #800000;
  }
  .ql-snow .ql-picker {
    color: #00cc00;
  }

  img {
    max-width: 300px;
    max-height: 300px;
  }

  .ql-snow.ql-toolbar button:hover,
  .ql-snow .ql-toolbar button:hover,
  .ql-snow.ql-toolbar button:focus,
  .ql-snow .ql-toolbar button:focus,
  .ql-snow.ql-toolbar button.ql-active,
  .ql-snow .ql-toolbar button.ql-active,
  .ql-snow.ql-toolbar .ql-picker-label:hover,
  .ql-snow .ql-toolbar .ql-picker-label:hover,
  .ql-snow.ql-toolbar .ql-picker-label.ql-active,
  .ql-snow .ql-toolbar .ql-picker-label.ql-active,
  .ql-snow.ql-toolbar .ql-picker-item:hover,
  .ql-snow .ql-toolbar .ql-picker-item:hover,
  .ql-snow.ql-toolbar .ql-picker-item.ql-selected,
  .ql-snow .ql-toolbar .ql-picker-item.ql-selected {
    color: #80ff66;
  }

  .ql-snow .ql-stroke {
    stroke: white;
  }
  .ql-container.ql-snow {
    border: 1px solid #800000;
  }

  .ql-snow .ql-fill,
  .ql-snow .ql-stroke.ql-fill {
    fill: white;
  }
  .ql-snow.ql-toolbar button,
  .ql-snow .ql-toolbar button {
    width: 28px;
  }
  .quill {
    height: 400px;
  }

  .ql-container {
    font-size: 20px;
  }

  .ql-image {
    height: 30px;
  }
`;
const Top = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  margin-bottom: 50px;
  gap: 20px;
  h2 {
    width: 100%;
    font-size: 40px;
    font-weight: bold;
  }

  select {
    width: 110px;
    height: 40px;
    padding: 3px;
    font-size: 16px;
    background-color: rgba(0, 0, 0, 0.8);
    color: #00cc00;
    outline: none;
    border: 1px solid #800000;
  }
`;

const Title = styled.input`
  margin-top: 20px;
  padding: 10px;
  width: 100%;
  height: 40px;
  font-size: 16px;
  background-color: rgba(0, 0, 0, 0.8);
  color: #00cc00;
  outline: none;
  border: 1px solid #800000;

  &::placeholder {
    color: #ddff99;
  }
`;

const Bottom = styled.div`
  margin-top: 100px;
`;

const SubmitBtn = styled.input`
  width: 144px;
  height: 48px;
  color: #00cc00;
  float: left;
  border-radius: 4px;
  font-size: 18px;
  border: 1px solid #800000;
  background-color: rgba(0, 0, 0, 0.1);
  cursor: pointer;

  &:hover {
    color: #ddff99;
  }
  @media screen and (max-width: 400px) {
    width: 100px;
  }
`;

const CancelBtn = styled.input`
  width: 144px;
  height: 48px;
  float: right;
  border-radius: 4px;
  background-color: rgba(0, 0, 0, 0.1);
  border: 1px solid #800000;
  color: #00cc00;
  font-size: 18px;
  cursor: pointer;

  &:hover {
    color: #ddff99;
  }

  @media screen and (max-width: 400px) {
    width: 100px;
  }
`;
