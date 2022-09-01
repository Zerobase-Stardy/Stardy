import React from 'react'
import styled from 'styled-components'
import { useSelector } from "react-redux";
import cookies from "react-cookies";
import axios from "axios";

export default function Profile() {
  const user = useSelector((state) => state.userinfo.value);

  const ProfileBadge = function(){
    const profilImage = "https://w.namu.la/s/dffd0ffc5c1dc39d2debfea46e1019057c766153087117d5af327336a6fe997691ae78bda40eccb72fd68d7f17632d5f7a1080659dd031e4ac54b35272a36744d8c0e10ffae8726ca4fc476e29552120b7ad341b602afdea4d73806d548cf19d"
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
        <Email> Email: {user.email}</Email>
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
  font-family: Nanum;
`
const Email = styled.div`
  font-size: 16px;
  line-height: 18px;
  font-family: Nanum;
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