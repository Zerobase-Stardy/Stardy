import React from "react";
import { PieChart } from "react-minimal-pie-chart";
import styled from "styled-components";

export default function Pie() {
  return (
    <Wrap>
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
    </Wrap>
  );
}

const Wrap = styled.div`
  padding: 20px;
  border: 1px solid rgba(0, 204, 0, 0.6);
  box-shadow: 0 0 20px rgb(0 204 0 / 50%), inset 0 0 0 1px #000,
    inset 0 0 0 2px rgb(0 204 0 / 50%);
`;
const Title = styled.div`
  text-align: center;
  margin-bottom: 10px;
  font-size: 24px;
  color: #fff;
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
  color: #fff;
`;
const Terran = styled.div`
  width: 10px;
  height: 10px;
  background-color: #084b83;
`;
const Zerg = styled.div`
  width: 10px;
  height: 10px;
  background-color: #613dc1;
`;
const Protoss = styled.div`
  width: 10px;
  height: 10px;
  background-color: #eec170;
`;

const PieArea = styled.div`
  width: 100%;
  height: 200px;
`;
