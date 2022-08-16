import { createGlobalStyle } from "styled-components";
import reset from "styled-reset";  // style-reset 패키지
import Gowun from "./fonts/GowunDodum-Regular.ttf"
import Galmuri from "./fonts/Galmuri9.ttf"

const GlobalStyle = createGlobalStyle`
@font-face {
  font-family: "Gowun Dodum";
  src: url(${Gowun});

}

@font-face {
  font-family: "Galmuri";
  src: url(${Galmuri});
}

 ${reset}

*{
  
  box-sizing: border-box;
}


body{
font-family: "Gowun Dodum"

}
header
{
  width: 100%;
  background-color: black;
  color: #fff;
}

footer
{
  width: 100%;
  background-color: #252525;
  color: #fff;
}

a{
  text-decoration: none;
  color : inherit;
}
`;

export default GlobalStyle;