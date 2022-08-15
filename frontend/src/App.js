import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import ScrollToTop from "./components/ScrollToTop";
import Footer from "./Layout/Footer";
import Header from "./Layout/Header";
import Community from "./pages/Community";
import Home from "./pages/Home";
import Login from "./pages/Login";
import MyLecture from "./pages/MyLecture";
import Mypage from "./pages/Mypage";
import ProGamer from "./pages/ProGamer";
import Races from "./pages/Races";
import GlobalStyles from "./styles/GlobalStyles";


function App() {
  return (
    
<BrowserRouter>
<ScrollToTop />  {/* 페이지 이동시 스크롤 위로 고정하는 컴포넌트 */}
<GlobalStyles />
<Header />
<Routes>
<Route path={"/"} element={<Home />}></Route>
  <Route path={"/progamer"} element={<ProGamer />}></Route>
  <Route path={"/races"} element={<Races />}></Route>
  <Route path={"/community"} element={<Community />}></Route>
  <Route path={"/mylecture"} element={<MyLecture />}></Route>
  <Route path={"/login"} element={<Login />}></Route>
  <Route path={"/mypage"} element={<Mypage />}></Route>
</Routes>
<Footer />
</BrowserRouter>

  );
}

export default App;
