import styled from "styled-components";
import { Link } from "react-router-dom";

export default function NavHoverMenu(props) {
  return (
    <MenuItems style={props.style}>
      <ItemsUl>
        <li>
          <Link to="/races/terran"> <HoverEffect>테란 </HoverEffect> </Link>
        </li>
        <li>
          <Link to="/races/protoss"><HoverEffect>프로토스 </HoverEffect> </Link>
        </li>
        <li>
          <Link to="/races/zerg"> <HoverEffect>저그 </HoverEffect> </Link>
        </li>
      </ItemsUl>
    </MenuItems>
  );
}

const MenuItems = styled.div`
  margin-top: 5px;
  background-color: black;
  opacity: 0.8;
  width: 7vw;
  height: 25vh;
  position: absolute;
  left: -32px;
  z-index: 0;
  border-left: 1px solid #800000;
  border-right: 1px solid #800000;
  border-bottom: 1px solid #800000;
`;
const ItemsUl = styled.ul`
  width: 80%;
  height: 100%;
  display: flex;
  justify-content: space-around;
  flex-direction: column;
  padding: 4px 0 4px 12px;
  margin: 0;
  color: #80ff66;
`;
const HoverEffect = styled.div`
  cursor: pointer;
  text-align: center;
  &:hover {
    color: #ccff66;
    font-weight: bold;
  }
`;
