import React, { useState, useEffect } from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import axios from "axios";
import cookies from "react-cookies";
import { login } from "./redux/loginSlice";
import ScrollToTop from "./components/ScrollToTop";
import LoginModal from "./components/LoginModal";
import Footer from "./Layout/Footer";
import Header from "./Layout/Header";
import Home from "./pages/Home";
import Mypage from "./mypages/Mypage";
import ProGamer from "./pages/ProGamer";
import Races from "./pages/Races";
import GlobalStyles from "./styles/GlobalStyles";
import Post from "./pages/Post";
import PostDetail from "./pages/PostDetail";
import AddPost from "./pages/AddPost";
import Admin from "./admin/Admin";
import AdminLogin from "./admin/AdminLogin";
import AdminSignUp from "./admin/AdminSignUp";
import Attendance from "./mypages/Attendance";
import Profile from "./mypages/Profile";
import Zerg from "./Races/Zerg";
import Terran from "./Races/Terran";
import Protoss from "./Races/Protoss";
import Error401 from "./pages/Error401";
import KakaoHandle from "./components/KakaoHandle";
import ClassRoom from "./classRoom/ClassRoom";
import Bookmark from "./mypages/Bookmark";
import MyLecture from "./mypages/MyLecture";
import GoogleHandle from "./components/GoogleHandle";

function App() {
  const dispatch = useDispatch();
  const header = useSelector((state) => state.userinfo.value.header);
  const loginmodal = useSelector((state) => state.userinfo.loginmodal);
  useEffect(() => {
    const accessToken = cookies.load("accessToken");
    if (accessToken !== undefined) {
      axios
        .get("https://dokuny.blog/members/me", {
          headers: header,
        })
        .then((res) => {
          dispatch(
            login({
              login: true,
              email: `${res.data.data.email}`,
              nickname: `${res.data.data.nickname}`,
              point: `${res.data.data.point}`,
              header: {
                Authorization: `Bearer ${accessToken}`,
              },
            })
          );
        });
    }
  }, []);

  return (
    <BrowserRouter>
      <ScrollToTop /> {/* 페이지 이동시 스크롤 위로 고정하는 컴포넌트 */}
      <GlobalStyles />
      <Header />
      <Routes>
        <Route path={"/"} element={<Home />}></Route>
        <Route path={"/progamer"} element={<ProGamer />}></Route>
        <Route path={"races"} element={<Races />}>
          <Route path={"terran"} element={<Terran />}></Route>
          <Route path={"protoss"} element={<Protoss />}></Route>
          <Route path={"zerg"} element={<Zerg />}></Route>
        </Route>
        <Route path={"/post"} element={<Post />}></Route>
        <Route path={"/post/write"} element={<AddPost />}></Route>
        <Route path={"/post/:id"} element={<PostDetail />}></Route>
        <Route path={"/oauth/kakao"} element={<KakaoHandle />}></Route>
        <Route path={"/oauth/google"} element={<GoogleHandle />}></Route>

        <Route path={"/classRoom/:courseId"} element={<ClassRoom />}></Route>
        <Route path={"/mypage"} element={<Mypage />}></Route>
        <Route path={"/admin"} element={<Admin />}></Route>
        <Route path={"/admin/login"} element={<AdminLogin />}></Route>
        <Route path={"/admin/signup"} element={<AdminSignUp />}></Route>
        <Route path={"mypage"} element={<Mypage />}>
          <Route path={"profile"} element={<Profile />}></Route>
          <Route path={"attendance"} element={<Attendance />}></Route>
          <Route path={"bookmark"} element={<Bookmark />}></Route>
          <Route path={"mylecture"} element={<MyLecture />}></Route>
        </Route>
        <Route path={"/error401"} element={<Error401 />}></Route>
      </Routes>
      <Footer />
      {loginmodal ? <LoginModal /> : null}
    </BrowserRouter>
  );
}

export default App;
