package com.github.backend.admin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.backend.model.constants.Role;
import com.github.backend.model.dto.CreateAdmin;
import com.github.backend.persist.entity.Admin;
import com.github.backend.service.AdminService;
import com.github.backend.web.AdminController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = AdminController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class AdminControllerTest {

    @MockBean
    private AdminService adminService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Admin 생성 성공")
    void testSuccessAdminCreate() throws Exception{
        //given
        given(adminService.registerAdmin(anyString(), anyString()))
                .willReturn(
                        Admin.builder()
                                .adminId("admin")
                                .password("password")
                                .role(Role.ROLE_ADMIN)
                                .build()
                );
        //when
        Admin admin = adminService.registerAdmin(
                "admin",
                "password"
        );
        //then
        mockMvc.perform(post("/admin/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new CreateAdmin.Request(admin.getAdminId(), admin.getPassword())
                        ))
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.adminId").value("admin"))
                .andExpect(jsonPath("$.password").value("password"))
                .andExpect(jsonPath("$.role").value("ROLE_ADMIN"));
    }
}
