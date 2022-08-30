import { createSlice } from "@reduxjs/toolkit";

const loginSlice = createSlice({
  name: "login",
  initialState: {
    value: {
      login: false,
      email: "",
      nickname: "",
      point: 0,
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
