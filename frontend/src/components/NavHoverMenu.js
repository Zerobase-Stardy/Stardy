import styled from "styled-components";
import { Link } from "react-router-dom";

export default function NavHoverMenu(props) {
  const LinkItem = ({ children, to }) => (
    <Link to={to}>
      <HoverEffect> {children} </HoverEffect>
    </Link>
  );

  return (
    <MenuItems style={props.style}>
      <ItemsUl>
        <li>
          <LinkItem to="/races/terran"> 테란 </LinkItem>
        </li>
        <li>
          <LinkItem to="/races/protoss"> 프로토스 </LinkItem>
        </li>
        <li>
          <LinkItem to="/races/zerg"> 저그 </LinkItem>
        </li>
      </ItemsUl>
    </MenuItems>
  );
}

const MenuItems = styled.div`
  background-color: black;
  opacity: 0.8;
  width: 7vw;
  height: 20vh;
  position: absolute;
  left: -32px;
  z-index: 999;
`;
const ItemsUl = styled.ul`
  width: 80%;
  height: 100%;
  display: flex;
  justify-content: space-around;
  flex-direction: column;
  padding: 4px 0 4px 12px;
  margin: 0;
`;
const HoverEffect = styled.div`
  cursor: pointer;
  text-align: center;
  &:hover {
    color: white;
    font-weight: bold;
  }
`;
