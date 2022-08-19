import styled from 'styled-components';



export default function NavHoverMenu(props){
    return(
        <MenuItems style={props.style}>
            <ItemsList>
                <div></div>
                <ItemsUl>
                    <li style={{cursor: "pointer"}}>저그</li>
                    <li style={{cursor: "pointer"}}>테란</li>
                    <li style={{cursor: "pointer"}}>프로토스</li>
                </ItemsUl>
                <div></div>
                <div></div>
                <div></div>
            </ItemsList>
        </MenuItems>
    )
}


const MenuItems = styled.div`
  background-color: black;
  opacity: 0.8;
  width: 100%;
  height: 20vh;
  position: absolute;
`
const ItemsList = styled.div`
  width: 80%;
  height: 100%;
  display: flex;
  justify-content: space-between;
  margin: 0 auto;
`

const ItemsUl = styled.ul`
    display: flex;
    flex-direction: column;
    justify-content: space-around;
    padding: 0;
    opacity: 1;
`

