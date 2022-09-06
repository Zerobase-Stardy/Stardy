package com.github.backend.web;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.backend.config.SecurityConfig;
import com.github.backend.dto.admin.LoginAdmin;
import com.github.backend.persist.admin.Admin;
import com.github.backend.security.jwt.JwtAccessDeniedHandler;
import com.github.backend.security.jwt.JwtAuthenticationProvider;
import com.github.backend.security.jwt.JwtEntryPoint;
import com.github.backend.security.jwt.Tokens;
import com.github.backend.service.admin.impl.AdminService;
import com.github.backend.testUtils.WithMemberInfo;
import com.github.backend.web.admin.AdminLoginController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(value = AdminLoginController.class,
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)}
)
public class AdminLoginControllerTest {

    @MockBean
    JwtAuthenticationProvider jwtAuthenticationProvider;
    @MockBean
    JwtEntryPoint jwtEntryPoint;
    @MockBean
    AuthenticationConfiguration authenticationConfiguration;

    @MockBean
    JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @MockBean
    private AdminService adminService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMemberInfo(role = "ROLE_ADMIN")
    @DisplayName("어드민 로그인 성공")
    void testLoginAdmin() throws Exception {
        //given
        Admin admin = Admin.builder()
                .adminId("admin")
                .password("password")
                .build();

        Tokens tokens = Tokens.builder()
                .accessToken("access")
                .refreshToken("refresh")
                .build();

        given(adminService.loginAdmin(anyString(), anyString()))
                .willReturn(tokens);
        //when
        //then
        mockMvc.perform(post("/admin/login").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                        LoginAdmin.Request.builder()
                                                .adminId(admin.getAdminId())
                                                .password(admin.getPassword())
                                                .build()
                                )
                        ))
                .andDo(print())
                .andExpect(jsonPath("$.data.accessToken").value(tokens.getAccessToken()))
                .andExpect(jsonPath("$.data.refreshToken").value(tokens.getRefreshToken()))
                .andExpect(status().isOk());
    }
}
