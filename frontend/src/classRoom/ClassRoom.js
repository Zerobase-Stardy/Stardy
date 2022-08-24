import ReactPlayer from "react-player";
import { useState } from "react"
import styled from "styled-components";
import ClassRoomHeader from "./ClassRoomHeader";
import { HiHome, HiArrowSmLeft, HiArrowSmRight } from 'react-icons/hi';
import SideNav from "./SideNav";

export default function ClassRoom(){
    const [isOpen, setIsOpen] = useState(false)
    const [visible, setVisible] = useState({
        visibility: "hidden"
    })

    const openClick = () =>{
        if(!isOpen){
            setVisible({
                visibility: "visible"
            })
            setIsOpen(true)
        } else {
            setVisible({
                visibility: "hidden"
            })
            setIsOpen(false)
        }
    }

    


    return(
        <Bgc>
            <SideNav style={visible} openClick={openClick}/>
            <ClassRoomHeader openClick={openClick}/>
            <BodyStyle>
                <Video>
                    <ReactPlayer url='https://www.youtube.com/watch?v=jbX0no1fQRE' width="100%" height="100%" controls />
                </Video>
            </BodyStyle>

            <BottomArea>
                <ButtonGroup>
                    <ButtonStyle> <HiHome /> </ButtonStyle>
                    <ButtonStyle> <HiArrowSmLeft /> </ButtonStyle>
                    <ButtonStyle> <HiArrowSmRight /> </ButtonStyle>
                </ButtonGroup>

            </BottomArea>
        </Bgc>
    )
}


const Bgc = styled.div`
    background-color: black;
    display: flex;
    flex-direction: column;
    height: 100vh;
`
const Video = styled.div`
    width: 65vw;
    height: 78vh;
`


const ButtonGroup = styled.div`
    position: absolute;
    right: 4px;
    bottom: 4px;
`

const ButtonStyle = styled.button`
    color: black;
    background-color: none;
    font-size: 20px;
    cursor: pointer;
    margin: 2px;
    border: none;
`

const BodyStyle = styled.div`
    width: 80%;
    height: 100%;
    border-radius: 10px;
    background-color: gray;
    margin: 10px auto;
    display: flex;
    align-items: center;
    justify-content: center;
`


const BottomArea = styled.div`
    border: 1px solid gray;
    height: 45px;
    position: relative;
`


