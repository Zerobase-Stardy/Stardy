import React from 'react'
import styled from 'styled-components';
import {RiCloseLine} from 'react-icons/ri'
import GoogleButton from './GoogleButton';
import KaKaoButton from './KaKaoButton';


export default function Login(props) {
  return (
    <Modal>
      <LoginArea>
       <CloseBtn onClick={props.toggle}> <RiCloseLine /></CloseBtn>
        <h1>Login</h1>
        <ButtonArea>      <GoogleButton />
        <KaKaoButton /></ButtonArea>
  
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
background-color: rgba(0, 0, 0, 0.8);

`

const LoginArea = styled.div`
position: fixed;
border-radius: 20px;
top: 45%;
left: 50%;
transform: translate(-50%, -50%);
width: 450px;
height: 500px;
background-color: white;
padding: 30px;
text-align: center;

h1{
  font-size: 24px;
}
`

const CloseBtn = styled.div`
width: 100%;
text-align: right;
font-size: 24px;
`

const ButtonArea = styled.div`
width: 100%;
display:  grid;

`