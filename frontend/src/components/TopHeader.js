import styled from "styled-components";


const TopItem = ({active, children, to}) => {
    <div className="menu-item"> 
        {children}
    </div>
}


export default function TopHeader(){
    return (
        <div>
            <TopItem>
                <img src="/" alt=""/>  STARdy
            </TopItem>
            <TopItem>MyPage</TopItem>
            
        </div>
    )
}

const Logo = styled.div`
    
`