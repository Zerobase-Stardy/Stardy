
import styled from "styled-components";
import { HiOutlineX } from "react-icons/hi"

export default function SideNav({info, openClick, style}){
    return (
        <Nav style={style}> {info.title}
                <CloseButton onClick={openClick}>
                    <HiOutlineX />
                </CloseButton>
                <SideNavMenu>
                    게이머: {info.gamerName}
                </SideNavMenu>
                <SideNavMenu>
                    강의 난이도: {info.level}
                </SideNavMenu>
        </Nav>
    )
}


const Nav = styled.ul`
    box-sizing: border-box;
    box-shadow: 10px 2px 15px -7px white;
    position: absolute;
    height: 100%;
    width: 24vw;
    background-color: black;
    top: 0;
    left: 0;
    margin:0;
    padding: 20px 8px;
    font-size: 24px;
    font-weight: bold;
    color: white;
    z-index: 999;
    border-radius: 4px;
`


const SideNavMenu = styled.li`
    background-color: white;
    list-style: none;
    margin: 32px 12px;
    text-align: center;
    font-weight: none;
    color: black;
    font-size: 18px;
    border-radius: 24px;
    height: 40px;
    display: block;
    line-height: 40px;
`

const CloseButton = styled.div`
    cursor: pointer;
    border-radius: 50%;
    border: 2px solid white;
    display: inline-block;
    width: 32px;
    height: 32px;
    position: absolute;
    top: 20px;
    right: 8px;
    text-align: center;
    color: white;
`
