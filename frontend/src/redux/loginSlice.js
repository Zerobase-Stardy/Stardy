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
    loginmodal: false,
  },

  reducers: {
    login: (state, action) => {
      state.value = action.payload;
    },
    modal: (state, action) => {
      state.loginmodal = action.payload;
    },
  },
});

export const { login, modal } = loginSlice.actions;
export default loginSlice;
