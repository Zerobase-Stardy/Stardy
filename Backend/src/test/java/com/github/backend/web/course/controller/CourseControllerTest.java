package com.github.backend.web.course.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.backend.config.SecurityConfig;
import com.github.backend.dto.course.CourseInfoOutputDto;
import com.github.backend.persist.course.Course;
import com.github.backend.persist.gamer.Gamer;
import com.github.backend.security.jwt.JwtAccessDeniedHandler;
import com.github.backend.security.jwt.JwtAuthenticationProvider;
import com.github.backend.security.jwt.JwtEntryPoint;
import com.github.backend.security.oauth.CustomOAuth2UserService;
import com.github.backend.security.oauth.OAuth2AuthenticationFailureHandler;
import com.github.backend.security.oauth.OAuth2AuthenticationSuccessHandler;
import com.github.backend.service.course.impl.CourseService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = CourseController.class,
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)}
)
class CourseControllerTest {
    @MockBean
    JwtAuthenticationProvider jwtAuthenticationProvider;
    @MockBean
    JwtEntryPoint jwtEntryPoint;
    @MockBean
    AuthenticationConfiguration authenticationConfiguration;
    @MockBean
    OAuth2AuthenticationSuccessHandler oAuth2SuccessHandler;
    @MockBean
    OAuth2AuthenticationFailureHandler oAuth2FailureHandler;
    @MockBean
    CustomOAuth2UserService oAuth2UserService;
    @MockBean
    JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @MockBean
    private CourseService courseService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;


    @Test
    @DisplayName("강의 정보 검색 성공")
    void testSuccessSearchCourses() throws Exception {
        //given
        Gamer gamer = Gamer.builder()
                .id(1L)
                .name("유영진")
                .race("테란")
                .nickname("rush")
                .introduce("단단한 테란")
                .build();

        Course course = Course.builder()
                .id(1L)
                .gamer(gamer)
                .title("벙커링")
                .videoUrl("https://www.youtube.com/watch?v=2rpu0f-qog4")
                .thumbnailUrl("https://img.youtube.com/vi/2rpu0f-qog4/default.jpg")
                .comment("세상에서 제일 쉬운 8배럭 벙커링 강의")
                .level("입문")
                .race("테란")
                .price(10L)
                .build();

        List<CourseInfoOutputDto.Info> courseList = new ArrayList<>();
        courseList.add(CourseInfoOutputDto.Info.of(course));

        given(courseService.searchCourseList(any()))
                .willReturn(courseList);
        //when

        //then
        mockMvc.perform(get("/course/courses"))
                .andDo(print())
                .andExpect(jsonPath("$.data[0].title").value(course.getTitle()))
                .andExpect(jsonPath("$.data[0].videoUrl").value(course.getVideoUrl()))
                .andExpect(jsonPath("$.data[0].thumbnailUrl").value(course.getThumbnailUrl()))
                .andExpect(jsonPath("$.data[0].comment").value(course.getComment()))
                .andExpect(jsonPath("$.data[0].level").value(course.getLevel()))
                .andExpect(jsonPath("$.data[0].race").value(course.getRace()))
                .andExpect(jsonPath("$.data[0].price").value(course.getPrice()))
                .andExpect(status().isOk());
    }
}