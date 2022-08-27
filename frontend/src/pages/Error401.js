import React from "react";
import { Link } from "react-router-dom";
export default function Error401() {
  return (
    <div>
      잘못된 접근 입니다.
      <div
        style={{
          fontSize: "30px",
          marginTop: "20px",
          color: "blue",
          textDecorationLine: "underline",
        }}
      >
        <Link to="/">홈으로 가기</Link>
      </div>
    </div>
  );
}
