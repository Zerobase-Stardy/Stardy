import styled from 'styled-components';



export default function NavHoverMenu(props){
    return(
        <MenuItems style={props.style}>
            <ItemsUl>
                    <li style={{cursor: "pointer", textAlign: "center" }}>저그</li>
                    <li style={{cursor: "pointer", textAlign: "center" }}>테란</li>
                    <li style={{cursor: "pointer", textAlign: "center" }}>프로토스</li>
            </ItemsUl>
        </MenuItems>
    )
}


const MenuItems = styled.div`
  background-color: black;
  opacity: 0.8;
  width: 7vw;
  height: 20vh;
  position: absolute;
  left: -32px
`
const ItemsUl = styled.ul`
  width: 80%;
  height: 100%;
  display: flex;
  justify-content: space-around;
  flex-direction: column;
  padding: 4px 0 4px 12px;
  margin: 0;
`
