import { createGlobalStyle } from "styled-components";
import reset from "styled-reset"; // style-reset 패키지
import Starcraft from "./fonts/Starcraft.ttf";
import Nanum from "./fonts/NanumGothic-Regular.ttf";
import NanumBold from "./fonts/NanumGothic-Bold.ttf";

const GlobalStyle = createGlobalStyle`

@font-face{
  font-family: "Nanum";
  src: url(${Nanum});
}

@font-face{
  font-family: "NanumBold";
  src: url(${NanumBold});
}

@font-face{
  font-family: "Starcraft";
  src: url(${Starcraft});
}


 ${reset}

*{
  box-sizing: border-box;
}



h1,h2{
  font-family: NanumBold;
  font-size: 30px;
}


body{
padding-top: 64px;
font-family: Nanum;
background-color: black;
background-image: url("https://static.starcraft.com/production/images/site/backdrops/backdrop-stars.890c5929ec65159852db3a0fab438e7aaa5c210f.jpg");

}

header
{
  width: 100%;
  background-color: black;
  color: #fff;
  position: fixed;
  top:0;
  left:0;
  right:0;
  z-index: 1;
  font-family: Nanum;
  opacity: 0.9;
  
}

main{
  padding-bottom: 100px;
}
footer
{
  width: 100%;
  background-color: black;
  color: #fff;
}

a{
  text-decoration: none;
  color : inherit;
}
`;

export default GlobalStyle;
