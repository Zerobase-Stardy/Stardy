import React, { useState, useRef, useMemo, useEffect } from "react";
import { useSelector } from "react-redux";
import ReactQuill, { Quill } from "react-quill";
import { CustomToolbar } from "../Layout/CustomToolbar";
import axios from "axios";
import ImageResize from "quill-image-resize-module-react";
import "react-quill/dist/quill.snow.css";
import styled from "styled-components";

Quill.register("modules/imageResize", ImageResize);

export default function Editor(props) {
  const quillRef = useRef();
  const header = useSelector((state) => state.userinfo.value.header);

  const imageHandler = () => {
    const input = document.createElement("input");

    input.setAttribute("type", "file");
    input.setAttribute("accept", "image/*");
    input.click();

    input.onchange = () => {
      const formData = new FormData();
      const file = input.files[0];
      formData.append("image", file);
      axios
        .post("https://dokuny.blog/postImage", formData, { headers: header })
        .then((res) => {
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
  const [text, setText] = useState("");

  useEffect(() => {
    props.writing(text);
  });

  const handleText = (value) => {
    setText(value);
  };

  useEffect(() => {});

  return (
    <Wrap>
      <div className="text-editor">
        <CustomToolbar />
        <ReactQuill
          ref={quillRef}
          modules={modules}
          formats={formats}
          placeholder="내용을 입력해 주세요"
          value={text}
          onChange={handleText}
        />
      </div>
    </Wrap>
  );
}

const Wrap = styled.div`
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
