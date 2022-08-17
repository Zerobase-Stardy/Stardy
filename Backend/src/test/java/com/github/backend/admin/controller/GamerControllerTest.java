package com.github.backend.admin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.backend.model.constants.Role;
import com.github.backend.model.dto.CreateAdmin;
import com.github.backend.model.dto.RegisterGamer;
import com.github.backend.persist.entity.Admin;
import com.github.backend.persist.entity.Gamer;
import com.github.backend.service.GamerService;
import com.github.backend.web.GamerController;
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
@WebMvcTest(value = GamerController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class GamerControllerTest {

    @MockBean
    private GamerService gamerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Gamer 등록 성공")
    void testSuccessGamerRegister() throws Exception{
        //given
        given(gamerService.registerGamer(
                anyString(),
                anyString(),
                anyString(),
                anyString()))
                .willReturn(
                        Gamer.builder()
                                .name("유영진")
                                .race("테란")
                                .nickName("rush")
                                .introduce("단단한 테란")
                                .build()
                );

        //when
        Gamer gamer = gamerService.registerGamer(
                "유영진",
                "테란",
                "rush",
                "단단한 테란"
        );
        
        //then
        mockMvc.perform(post("/gamer/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new RegisterGamer.Request(
                                        gamer.getName(),
                                        gamer.getRace(),
                                        gamer.getNickName(),
                                        gamer.getIntroduce()
                                )
                        ))
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("유영진"))
                .andExpect(jsonPath("$.race").value("테란"))
                .andExpect(jsonPath("$.nickName").value("rush"))
                .andExpect(jsonPath("$.introduce").value("단단한 테란"));
    }
}
