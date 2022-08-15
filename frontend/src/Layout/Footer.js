import React from 'react'
import styled from 'styled-components';
import {FaGithub} from 'react-icons/fa'
import {SiNotion , SiSpring, SiSpringboot, SiSpringsecurity, SiMariadb, SiHtml5, SiCss3, SiReact, SiJavascript} from 'react-icons/si'



export default function Footer() {


  return (
    <footer>
      <Wrap>
        <Logo>Stardy</Logo>
        <Top>
        <a href="https://github.com/Zerobase-Stardy"><FaGithub /></a>
        <a href="https://dokuny.notion.site/Stardy-1a5d71be1ff149c8b8495a148c1bca3c"><SiNotion /></a>
        </Top>
        <Bottom>
          <Member>
          <h1>Member</h1>
          <div>BackEnd : 이도훈  , 노영훈  , 김현빈</div> 
          <div>FrontEnd : 임호준  , 이승훈</div>
          </Member>
          <Tools>
          <h1>Tools</h1>
              <div>
              BackEnd : &nbsp;
              <SiSpring color='#6aad3c'/>&nbsp;&nbsp;
              <SiSpringboot color='#6aad3c'/>&nbsp;&nbsp;
              <SiSpringsecurity color='#6aad3c'/>&nbsp;&nbsp;
              <SiMariadb />
              </div>

              <div>FrontEnd : &nbsp;
              <SiHtml5 color='#ea6329'/>&nbsp;&nbsp;
              <SiCss3 color='#156eb0'/>&nbsp;&nbsp;
              <SiJavascript color='#f6d236'/>&nbsp;&nbsp;
              <SiReact color='skyblue'/>
              </div>
            </Tools>

        </Bottom>



      </Wrap>

    </footer>
  )
}



const Wrap = styled.div`
padding: 20px;
width: 80%;
margin: 0 auto;
display: grid;
gap:20px
`
const Logo = styled.h1`
margin: 0 auto;
font-size: 36px;
`

const Top = styled.div`
margin: 0 auto;
display: flex;
font-size: 30px;
gap: 20px;
`

const Bottom = styled.div`
margin: 0 auto;
text-align: center;
display: grid;
gap: 20px;
font-size: 17px;

`

const Member = styled.div`
display: grid;
gap: 10px;

h1{
  font-size: 24px;
}
`

const Tools = styled.div`
display: grid;
gap: 10px;
h1{
  font-size: 24px;
}
`

