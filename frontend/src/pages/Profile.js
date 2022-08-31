import React from 'react'
import styled from 'styled-components'
import { useSelector } from "react-redux";
import cookies from "react-cookies";
import axios from "axios";

export default function Profile() {
  const user = useSelector((state) => state.userinfo.value);

  const ProfileBadge = function(){
    const profilImage = "https://via.placeholder.com/150"
    const badgeStyle = { borderRadius: "50%", 
                        width: "100px", 
                        height: "100px",
                        margin: "5% 5% 5% 0",
                      }
    return <img src={profilImage} alt="프로필사진" style={badgeStyle} />
  }


  ImgSample.defaultProps = { src:"http://i.ytimg.com/vi/iF_PQbkllM8/mqdefault.jpg"}
  return (
    <div>
      <UserInfo>
        <div style={{display: "flex"}}>
          <ProfileBadge/>
          <div style={{alignSelf: "center"}}>
            <NickName > {user.nickname} </NickName>
            <Point> 출석 포인트: {user.point} </Point>
          </div>
        </div>
        <Email> 이메일: <br/> {user.email}</Email>
      </UserInfo>
      <div style={{margin: "5% 0 0 24px"}}>수강한 강의</div>
      <UserVideo>
        <ImgSample/>
        <ImgSample/>
        <ImgSample/>
        <ImgSample/>
        <ImgSample/>
        <ImgSample/>
      </UserVideo>
    </div>
  )
}

const UserInfo = styled.div` 
  margin: 8% 0 0 24px;
`

const NickName = styled.div`
  font-size: 32px;
  font-weight: bold;
  margin: 10% 0 10% 0;

`

const Point = styled.div`
  font-size: 12px;
`
const Email = styled.div`
  width: 200px;
  height: 36px;
  font-size: 16px;
  background-color:  #b2b2b2;
  line-height: 18px;
`

const UserVideo = styled.div`
  display: grid;
  grid-template-columns: 1fr 1fr 1fr;
  grid-template-rows: 1fr 1fr;
`

const ImgSample = styled.img`
width: 160px;
margin: 8px;
background-color: gray;
`