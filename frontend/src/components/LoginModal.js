import React, {useEffect, useRef} from 'react'
import styled from 'styled-components';
import {RiCloseLine} from 'react-icons/ri'
import GoogleButton from './GoogleButton';
import KaKaoButton from './KaKaoButton';


export default function Login(props) {
  const loginRef = useRef();
  const {toggle,openModal} = props;


  function handleClickOutside(event){
    if(openModal && !loginRef.current.contains(event.target)){
      toggle()
    }
  }
  useEffect(() => {
    document.addEventListener("mousedown", handleClickOutside);
    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  });

  return (
    <Modal>
      <LoginArea ref={loginRef}>
       <CloseBtn> <RiCloseLine onClick={toggle}/></CloseBtn>
        <h1>LOGIN</h1>
        <ButtonArea>      
          <GoogleButton />
        <KaKaoButton />
        </ButtonArea>
  
      </LoginArea>
    </Modal>
  )
}

const Modal= styled.div`
position: fixed;
top: 0;
left: 0;
right: 0;
bottom: 0;
width: 100%;
height: 100vh;
background-color: rgba(0, 0, 0, 0.85);


`

const LoginArea = styled.div`
position: fixed;
border-radius: 10px;
top: 45%;
left: 50%;
transform: translate(-50%, -50%);
width: 400px;
height: 400px;
background-color: white;
padding: 30px;
text-align: center;
background-color: rgba(120,120,120,0.8);
color : white;


;

h1{
  font-size: 24px;
  font-weight: bold;
}
`

const CloseBtn = styled.div`
width: 100%;
text-align: right;
font-size: 24px;

svg
{
  cursor: pointer;

  &:hover{
    color: black;
  }
}
`

const ButtonArea = styled.div`
width: 100%;
display:  grid;
margin-top: 50px;
gap: 20px;
`