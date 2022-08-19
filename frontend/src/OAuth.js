const CLIENT_ID = "c193668948af94877eadfecc1ced7a67";
const REDIRECT_URI =  "http://localhost:3000/oauth";

export const KAKAO_AUTH_URL = `https://kauth.kakao.com/oauth/authorize?client_id=${CLIENT_ID}&redirect_uri=${REDIRECT_URI}&response_type=code`;