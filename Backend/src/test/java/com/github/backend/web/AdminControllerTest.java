package com.github.backend.web;

import com.github.backend.model.dto.*;
import com.github.backend.persist.entity.Gamer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.backend.model.constants.Role;
import com.github.backend.persist.entity.Admin;
import com.github.backend.persist.entity.Course;
import com.github.backend.persist.entity.Gamer;
import com.github.backend.service.AdminService;
import com.github.backend.web.AdminController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = AdminController.class)
public class AdminControllerTest {

    @MockBean
    private AdminService adminService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    @DisplayName("게이머 리스트 조회 성공")
    void testSuccessGetGamerList() throws Exception {
        //given
        Gamer gamer = Gamer.builder()
                .name("유영진")
                .race("테란")
                .nickname("rush")
                .introduce("단단한 테란")
                .build();
        List<Gamer> gamerList = new ArrayList<>();
        gamerList.add(gamer);

        given(adminService.getGamerList(any()))
                .willReturn(gamerList);
        //when

        //then
        mockMvc.perform(get("/admin/gamer/list"))
                .andDo(print())
                .andExpect(jsonPath("$.data[0].name").value(gamer.getName()))
                .andExpect(jsonPath("$.data[0].race").value(gamer.getRace()))
                .andExpect(jsonPath("$.data[0].nickname").value(gamer.getNickname()))
                .andExpect(jsonPath("$.data[0].introduce").value(gamer.getIntroduce()))
                .andExpect(status().isOk());
    }


}