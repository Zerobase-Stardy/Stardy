import React from "react";
import { PieChart } from "react-minimal-pie-chart";
import styled, { withTheme } from "styled-components";

export default function Pie() {
  const data = [
    { title: "Cats", value: 20, color: "#E81A0C" },
    { title: "Dogs", value: 35, color: "#FE6900" },
    { title: "Birds", value: 10, color: "#FEB912" },
    { title: "Cats", value: 12, color: "#039C55" },
    { title: "Dogs", value: 18, color: "#2C81FB" },
    { title: "Birds", value: 5, color: "#702BFE" },
  ];

  return (
    <Wrap>
      <Title>종족 Pick 률</Title>
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
            {
              title: "Protoss",
              value: 42.57,
              color: "rgba(243, 255, 5, 0.46)",
            },
            { title: "Zerg", value: 28.52, color: "rgba(255, 5, 5, 0.46)" },
            { title: "Terran", value: 28.9, color: "rgba(5, 163, 255, 0.46)" },
          ]}
          label={({ dataEntry }) => dataEntry.value + "%"}
          labelStyle={{
            fontSize: "7px",
            fill: "#80ff66",
          }}
          background="1px solid rgba(0, 204, 0, 0.6)"
          animate
          animationDuration={2500}
        />{" "}
      </PieArea>
    </Wrap>
  );
}

const Wrap = styled.div`
  padding: 20px;

  border: 1px solid rgba(0, 204, 0, 0.6);
  box-shadow: 0 0 20px rgb(0 204 0 / 50%), inset 0 0 0 1px #000,
    inset 0 0 0 2px rgb(0 204 0 / 50%);

  &:hover {
    border-color: #ccff66;
    box-shadow: 0 0 10px #ccff66, inset 0 0 0 1px #000, inset 0 0 0 2px #ccff66;
    color: #ccff66;
  }
`;
const Title = styled.div`
  text-align: center;
  margin-bottom: 10px;
  font-size: 24px;
  color: #80ff66;
`;
const Top = styled.div`
  display: flex;
  gap: 8px;

  margin-bottom: 10px;
`;
const Race = styled.div`
  display: flex;
  font-size: 1px;
  color: #80ff66;
`;
const Terran = styled.div`
  width: 10px;
  height: 10px;
  background-color: rgba(5, 163, 255, 0.46);
`;
const Zerg = styled.div`
  width: 10px;
  height: 10px;
  background-color: rgba(255, 5, 5, 0.46);
`;
const Protoss = styled.div`
  width: 10px;
  height: 10px;
  background-color: rgba(243, 255, 5, 0.46);
`;

const PieArea = styled.div`
  width: 100%;
  height: 200px;
`;
