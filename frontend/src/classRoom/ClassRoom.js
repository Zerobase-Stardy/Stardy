import ReactPlayer from "react-player";
import { useEffect, useState } from "react"
import styled from "styled-components";
import ClassRoomHeader from "./ClassRoomHeader";
import { HiHome, HiArrowSmLeft, HiArrowSmRight } from 'react-icons/hi';
import SideNav from "./SideNav";
import { useParams } from "react-router-dom";
import axios from "axios";

export default function ClassRoom(){
    const [isOpen, setIsOpen] = useState(false)
    const [visible, setVisible] = useState({
        visibility: "hidden"
    })
    const [url, setUrl] = useState("")
    const { id } = useParams();

    console.log( id )

    // useEffect(() => {
    //     axios.get(`https://www.dokuny.blog:443/course/courses?`)
    //     .then((res) => {
    //         console.log( res.data.data.content.videoUrl )
    //     })
    // })


    console.log(url)

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
                <RedBox>
                    <Video>
                        <ReactPlayer url={url} width="100%" height="100%" controls />
                    </Video>
                </RedBox>
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
    background-image: url("https://static.starcraft.com/production/images/site/backdrops/backdrop-stars.890c5929ec65159852db3a0fab438e7aaa5c210f.jpg");
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
    width: 70%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.8);
    border: 2px solid #331f1f;
    border-radius: 6px;
    margin: 10px auto;
    display: flex;
    padding: 5px;
`

const RedBox = styled.div`
box-sizing: border-box;
width: 100%;
display: flex;
align-items: center;
justify-content: center;
border: 1px solid #800000;
`


const BottomArea = styled.div`
    height: 45px;
    position: relative;
`


