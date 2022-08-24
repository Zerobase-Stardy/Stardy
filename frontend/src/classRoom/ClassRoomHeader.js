import { useState } from "react"
import {  HiMenu, HiUser } from 'react-icons/hi';
import styled from 'styled-components';

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
            <div style={{backgroundColor: "gray", cursor: "pointer"}} onClick={props.openClick}> <HiMenu size="40"/> </div>
            <WordArea> 강의제목 </WordArea>
            <BadgeArea onClick={iconHandler}> <HiUser/> 
                <UserMenu style={ userInfo }>
                    <UserMenuList>마이 페이지</UserMenuList>
                    <UserMenuList> 커뮤니티 </UserMenuList>
                    <UserMenuList> 로그아웃 </UserMenuList>
                </UserMenu>
            </BadgeArea>
        </Header>
    )
}

const Header = styled.header`
    width: 100%;
    height: 40px;
    border: 1px solid gray;
    color: white;
    display: flex;
    justify-content: space-between;
    border-color: gray;
    font-size: 24px;
    font-weight: bold;
    text-align: center;
`
const WordArea = styled.span`
    position: absolute;
    left: 68px;
    top: 12px;
`


const BadgeArea = styled.div`
    background-color: gray;
    border: 1px solid gray;
    border-radius: 50%;
    width: 32px;
    height: 32px;
    margin: 5px 8px;
    cursor: pointer;
    position: relative
`


const UserMenu = styled.ul`
    
    font-size: 12px;
    background-color: gray;
    position: absolute;
    color: white;
    top: 40px;
    right: 0;
    width: 96px;
    height: 120px;
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