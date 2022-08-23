import React, { useState, useEffect } from "react";
import styled from "styled-components";
import { Link } from "react-router-dom";
import AdminMember from "./AdminMember";
import AdminGamer from "./AdminGamer";
import AdminLecture from "./AdminLecture";

export default function Admin() {
  const [checked, setChecked] = useState("gamer");

  return (
    <Wrap>
      <AdminHeader>
        <Radio>
          <Input
            name="admin"
            type="radio"
            id="gamer"
            onClick={(e) => {
              setChecked(e.target.id);
            }}
            defaultChecked
          />
          <Label htmlFor={"gamer"}>게이머</Label>

          <Input
            name="admin"
            type="radio"
            id="member"
            onClick={(e) => {
              setChecked(e.target.id);
            }}
          />
          <Label htmlFor={"member"}>유저</Label>
          <Input
            name="admin"
            type="radio"
            id="lecture"
            onClick={(e) => {
              setChecked(e.target.id);
            }}
          />
          <Label htmlFor={"lecture"}>강의</Label>
        </Radio>

        <Link to="/">
          <LogoutBtn>로그아웃</LogoutBtn>
        </Link>
      </AdminHeader>
      <DataArea>
        {checked === "gamer" && <AdminGamer />}
        {checked === "member" && <AdminMember />}
        {checked === "lecture" && <AdminLecture />}
      </DataArea>
    </Wrap>
  );
}

const Wrap = styled.div`
  display: grid;
`;

const AdminHeader = styled.div`
  width: 80%;
  margin: auto;
  height: 50px;
  display: flex;

  a {
    margin-left: auto;
  }
`;

const LogoutBtn = styled.div`
  line-height: 50px;
  cursor: pointer;
`;

const Radio = styled.div`
  display: flex;
  gap: 50px;

  input[type="radio"] {
    width: 0;
    height: 0;
    position: absolute;
  }
  input[type="radio"] + label {
    margin: 0;
    padding: 10px 20px;
    box-sizing: border-box;
    position: relative;
    display: inline-block;
    border: solid 1px #ddd;
    border-radius: 4px;
    background-color: #fff;
    line-height: 140%;
    text-align: center;

    cursor: pointer;
  }
  input[type="radio"]:checked + label {
    background-color: #87c3a1;
    color: #fff;
    border-color: #87c3a1;
  }
`;

const Input = styled.input``;
const Label = styled.label``;

const DataArea = styled.div`
  width: 80%;
  margin: auto;
`;
