import React from "react";
import { PieChart } from "react-minimal-pie-chart";
import styled from "styled-components";

export default function Pie() {
  return (
    <>
      <Title>
        종족 <span>Pick</span> 률
      </Title>
      <Top>
        <Race>
          Terran : &nbsp; <Terran />
        </Race>
        <Race>
          Zerg : &nbsp; <Zerg />
        </Race>
        <Race>
          Protoss : &nbsp; <Protoss />
        </Race>
      </Top>
      <PieArea>
        <PieChart
          data={[
            { title: "Protoss", value: 42.57, color: "#eec170" },
            { title: "Zerg", value: 28.52, color: "#613dc1" },
            { title: "Terran", value: 28.9, color: "#084b83" },
          ]}
          label={({ dataEntry }) => dataEntry.value + "%"}
          labelStyle={{
            fontSize: "7px",
            fill: "#fff",
          }}
          animate
          animationDuration={2500}
        />
      </PieArea>
    </>
  );
}

const Title = styled.div`
  text-align: center;
  margin-bottom: 10px;
  font-size: 24px;
  span {
    color: #ff6e7f;
  }
`;
const Top = styled.div`
  display: flex;
  gap: 8px;

  margin-bottom: 10px;
`;
const Race = styled.div`
  display: flex;
  font-size: 1px;
`;
const Terran = styled.div`
  width: 15px;
  height: 15px;
  background-color: #084b83;
`;
const Zerg = styled.div`
  width: 15px;
  height: 15px;
  background-color: #613dc1;
`;
const Protoss = styled.div`
  width: 15px;
  height: 15px;
  background-color: #eec170;
`;

const PieArea = styled.div`
  width: 100%;
  height: 230px;
`;
