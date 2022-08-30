import React, { useEffect } from "react";
import axios from "axios";
import cookies from "react-cookies";
export default function KakaoHandle() {
  const expires = new Date();

  let code = new URL(window.location.href).searchParams.get("code");
  const accessToken = cookies.load("accessToken");
  const header = {
    Authorization: `Bearer ${accessToken}`,
  };

  expires.setHours(expires.getHours() + 1);

  useEffect(() => {
    axios
      .get(`https://dokuny.blog/auth/login/kakao?code=${code}`)
      .then((res) => {
        cookies.save("accessToken", res.data.data.accessToken, {
          path: "/",
          expires,
        });
        cookies.save("refreshToken", res.data.data.refreshToken, {
          path: "/",
          expires,
        });
        document.location.href = "/";
      });
  }, []);

  return (
    <button
      onClick={() => {
        console.log(header);
        axios
          .get("https://dokuny.blog/members/me", {
            headers: header,
          })
          .then((res) => console.log(res.data.data));
      }}
    >
      요청
    </button>
  );
}
