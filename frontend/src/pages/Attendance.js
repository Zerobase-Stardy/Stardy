import React, { useEffect, useState } from 'react';
import Calendar from 'react-calendar';
import moment from "moment"
import '../styles/Calendar.css'; 
import styled from 'styled-components';
import { useSelector } from "react-redux";
import axios from 'axios';


export default function Attendance() {
  
    const [value, onChange] = useState(new Date());

    const needZero  = (date) => {
      return date > 10 ? date : "0"+ date
    }

    const day = `${value.getFullYear()}-${needZero( value.getMonth() + 1 )}-${needZero( value.getDate() )}`

    const [marks, setMarks] = useState([])
    let pick = [];

    const user = useSelector((state) => state.userinfo.value);
    const header = useSelector((state) => state.userinfo.value.header);


    const handleClick = () => {
      axios
        .post("https://www.dokuny.blog/members/me/attendances/daily",{}, {
          headers: header,
        })
        .catch((err) => console.log(err));


        alert(`출석하셨습니다. (포인트 +50p) 현재 포인트 ${user.point}`)
      }

    
    useEffect(() => {
      axios.get(`https://www.dokuny.blog/members/me/attendances?endDate=${day}&startDate=2000-01-01`, {
        headers: header,
      }).then( (res) => res.data.data.map((info) => {
        pick.push(info.date);
        setMarks([...pick]); 
      }))
      .catch((err) => console.log(err))
    }, [])


    return (
      <div style={{width: "505px", padding: "7%"}}>
        <Calendar onChange={onChange}  
                  formatDay={(locale, date) => moment(date).format("DD")}
                  value={value} 
                  tileContent={({ date, view }) => {
                    let html = [];
                    if (marks.find((x) => x === moment(date).format("YYYY-MM-DD"))) {
                      html.push(
                        <div style={{display:"flex", justifyContent:"center"}}>
                        <div className="dot"></div>
                        </div>
                      )
                    }
                    if ( day === moment(date).format("YYYY-MM-DD")){
                      html.push(<div className="today"></div>)
                    }
                    return (
                      <>
                          {html}
                      </>
                    );

                  }}
        />

        <AttendanceButton onClick={handleClick}>출석!</AttendanceButton>
      </div>
    )
}



const AttendanceButton = styled.button`
  width: 240px;
  height: 32px;
  font-size: 24px;
  line-height: 24px;
  box-sizing: border-box;
  padding: 4px;
  background-color: #1087ff;
  border: none;
  cursor: pointer;
  color: white;
  margin: 8px 0 0 0;
`