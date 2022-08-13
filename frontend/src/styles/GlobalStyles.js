import { createGlobalStyle } from "styled-components";
import reset from "styled-reset";  // style-reset 패키지

//전역적 스타일 작성하는 곳 입니다!

const GlobalStyle = createGlobalStyle`
 ${reset}



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