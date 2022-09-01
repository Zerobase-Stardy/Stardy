import { useState } from "react"
import {  HiMenu, HiUser } from 'react-icons/hi';
import styled from 'styled-components';
import { Link } from "react-router-dom";

export default function ClassRoomHeader(props){

    const [infoOpen, setinfoOpen] = useState(false)
    const [userInfo,setuserInfo] = useState({
        visibility : "hidden"
    })

    const iconHandler = (e) => {

        e.stopPropagation();
        
        if(!infoOpen){
            setuserInfo({ visibility : "visible" })
            setinfoOpen(true)
        } else {
            setuserInfo({ visibility : "hidden" })
            setinfoOpen(false)
        }
    }

    
    return(
        <Header>
            <div style={{backgroundColor: "gray", cursor: "pointer"}} onClick={props.openClick}> <HiMenu size="64"/> </div>
            <WordArea> {props.title} </WordArea>
            <BadgeArea onClick={iconHandler}> <HiUser size="32"/> 
                <UserMenu style={ userInfo }>
                    <UserMenuList>
                        <Link to="/mypage/profile">
                            마이 페이지
                        </Link>
                    </UserMenuList>
                    <UserMenuList> 
                        <Link to="/post">
                            커뮤니티 
                        </Link>
                    </UserMenuList>
                    <UserMenuList> 로그아웃 </UserMenuList>
                </UserMenu>
            </BadgeArea>
        </Header>
    )
}

const Header = styled.header`
    width: 100%;
    height: 64px;
    border: 1px solid gray;
    color: white;
    display: flex;
    justify-content: space-between;
    border-color: gray;
    font-size: 32px;
    font-weight: bold;
    text-align: center;
`
const WordArea = styled.span`
    position: absolute;
    left: 68px;
    top: 12px;
    line-height: 24px;
    font-family: NanumBold;
    padding: 4px;
`


const BadgeArea = styled.div`
    background-color: gray;
    border: 1px solid gray;
    border-radius: 50%;
    width: 40px;
    height: 40px;
    margin: 5px 8px;
    cursor: pointer;
    position: relative;
    line-height: 32px;
`


const UserMenu = styled.ul`
    font-size: 12px;
    background-color: gray;
    position: absolute;
    color: white;
    top: 52px;
    right: 0;
    width: 96px;
    height: 156px;
    border-radius: 15px;
    box-shadow: 4px 2px 15px 1px #000000;
    padding: 0;
    margin: 0;
`

const UserMenuList = styled.li`
    list-style: none;
    margin: 15px 0;
    text-decoration: underline;
`