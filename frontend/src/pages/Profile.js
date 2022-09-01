import React, { useEffect, useState } from 'react';
import styled from 'styled-components'
import { useSelector } from "react-redux";
import axios from 'axios';
import { Link } from "react-router-dom";

export default function Profile() {
  const user = useSelector((state) => state.userinfo.value);
  const header = useSelector((state) => state.userinfo.value.header);

  const ProfileBadge = function(){
    const profilImage = "https://w.namu.la/s/dffd0ffc5c1dc39d2debfea46e1019057c766153087117d5af327336a6fe997691ae78bda40eccb72fd68d7f17632d5f7a1080659dd031e4ac54b35272a36744d8c0e10ffae8726ca4fc476e29552120b7ad341b602afdea4d73806d548cf19d"
    const badgeStyle = { borderRadius: "50%", 
                        width: "100px", 
                        height: "100px",
                        margin: "5% 5% 5% 0",
                      }
    return <img src={profilImage} alt="프로필사진" style={badgeStyle} />
  }

  const [myvideo, setMyVideo] = useState([]);

  useEffect(() => {
    axios
    .get("https://www.dokuny.blog/members/me/courses", {
      headers: header,
    })
    .then((res) => {
      setMyVideo( [...res.data.data.content])
    })
    .catch((err) => console.log(err))
  }, [])


  const userVideo = myvideo.map((video) => {
    return (
      <Link to={`/classRoom/${video.courseId}`}>
        <LectureBox key={video.courseId}>
          <img src={video.thumbnail} alt="썸네일" style={{width:"100%", marginBottom: "4px"}}/>
          <div style={{fontSize:"14px"}}> {video.title} </div>
        </LectureBox>
      </Link>
    )
  })

  console.log(myvideo)
  
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
      <div style={{margin: "5% 0 0 24px"}}>구매한 강의</div>
      <UserVideo>
        {userVideo}
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
  margin: 8px;
`

const LectureBox = styled.div`
  width: 160px;
  margin: 0 16px 16px 0;
  cursor: pointer;
  :hover {
    transform: scale(1.1);
    border-radius: 5px;
    border-color: #ccff66;
    box-shadow: 0 0 10px #ccff66, inset 0 0 0 1px #000, inset 0 0 0 2px #ccff66;
    color: #ccff66;
  }

  &:active {
    transform: scale(1.2);
  }

`