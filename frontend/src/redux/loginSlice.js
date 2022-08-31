import { createSlice } from "@reduxjs/toolkit";
import cookies from "react-cookies";

const accessToken = cookies.load("accessToken");

const loginSlice = createSlice({
  name: "login",
  initialState: {
    value: {
      login: false,
      email: "",
      nickname: "",
      point: 0,
      header: {
        Authorization: `Bearer ${accessToken}`,
      },
    },
  },

  reducers: {
    login: (state, action) => {
      state.value = action.payload;
    },
  },
});

export const { login } = loginSlice.actions;
export default loginSlice;
