import React, { useState } from 'react';
import Calendar from 'react-calendar';
import moment from "moment"
import '../styles/Calendar.css'; 
import styled from 'styled-components';

export default function Attendance() {
  
    const [value, onChange] = useState(new Date());

    // const [marks, setMarks] = useState(["24-08-2022"])

    const handleClick = () => {

    }

    const marks = ["24-08-2022", "12-08-2022"]

    return (
      <div style={{width: "505px", padding: "7%"}}>
        <Calendar onChange={onChange}  
                  formatDay={(locale, date) => moment(date).format("DD")}
                  value={value} 
                  tileClassName={ ({ date }) => {
                     if (marks.find((x) => x === moment(date).format("DD-MM-YYYY"))) {
                          return 'highlight';
                      }
                  }}
        />

        <AttendanceButton>출석!</AttendanceButton>
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