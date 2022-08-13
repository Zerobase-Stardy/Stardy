import React from 'react'
import Nav from '../components/Nav'
import styled from 'styled-components';

const Wrap = styled.div`
width: 80%;
margin: 0 auto;

background-color: gray;
`

export default function Header() {
  return (
    <header>
      <Wrap> 
      <Nav />
      </Wrap>
    </header>
  )
}
