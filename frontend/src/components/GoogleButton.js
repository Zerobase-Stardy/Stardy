import React ,{ useEffect }from 'react';
import styled from "styled-components";
import GoogleLogin, { GoogleLogout } from 'react-google-login';
import {gapi} from 'gapi-script'


const clientId = process.env.REACT_APP_GOOGLE_CLIENT_ID;

export default function GoogleButton({ onSocial }){
useEffect (() => {
    function start () {
        gapi.client.init({
            clientId,
            scope:'email',
        });
    }

    gapi.load('client:auth2' , start);
}, []);

const onSuccess = (response) => {
    console.log(response)
}

const onFailure = (response) => {
    console.log(response);
};

    return(
        // <Google>
        //     <GoogleLogin
        //         clientId={clientId}
        //         responseType={"id_token"}
        //         onSuccess={onSuccess}
        //         onFailure={onFailure}
        //         buttonText = "Google로 로그인하기"
                
        //         />
        // </Google>

        <Button.Container>
                    <Button.GoogleButton 
                        clientId='asdf12345.apps.googleusercontent.com' // 발급된 clientId 등록
                        onSuccess={onSuccess}
                        onFailure={onFailure}
                        cookiePolicy={'single_host_origin'} // 쿠키 정책 등록
                        buttonText='Google 로그인' // 버튼에 사용될 텍스트
                    />
            </Button.Container>
    )
}

const Button = {
    Container: styled.div``,

    ButtonList: styled.div`
        display: flex;
        flex-direction: column;
        align-items: center;
    `,

    GoogleButton: styled(GoogleLogin)`
        width: 300px;
        height: 45px;
        margin: 6px 0;

        
        justify-content: center;

        & span {
            font-size: 15px;
            font-weight: 600 !important;
        }
    `,
}