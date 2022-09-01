package com.github.backend.web.course.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.backend.config.SecurityConfig;
import com.github.backend.dto.course.CourseInfoOutputDto;
import com.github.backend.dto.course.CourseInfoOutputDto.Info;
import com.github.backend.persist.course.Course;
import com.github.backend.persist.gamer.Gamer;
import com.github.backend.security.jwt.JwtAccessDeniedHandler;
import com.github.backend.security.jwt.JwtAuthenticationProvider;
import com.github.backend.security.jwt.JwtEntryPoint;
import com.github.backend.service.course.impl.CourseService;
import com.github.backend.testUtils.WithMemberInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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

        Course course2 = Course.builder()
                .id(2L)
                .gamer(gamer)
                .title("벙커링")
                .videoUrl("https://www.youtube.com/watch?v=2rpu0f-qog4")
                .thumbnailUrl("https://img.youtube.com/vi/2rpu0f-qog4/default.jpg")
                .comment("세상에서 제일 쉬운 8배럭 벙커링 강의")
                .level("입문")
                .race("테란")
                .price(10L)
                .build();

        List<Course> courseList = new ArrayList<>();
        courseList.add(course);
        courseList.add(course2);

        given(courseService.searchCourseList(any(), any()))
                .willReturn(new PageImpl<>(courseList).map(CourseInfoOutputDto.Info::of));
        //when

        //then
        mockMvc.perform(get("/course/courses"))
                .andDo(print())
                .andExpect(jsonPath("$.data.content[0].title").value(course.getTitle()))
                .andExpect(jsonPath("$.data.content[0].videoUrl").value(course.getVideoUrl()))
                .andExpect(jsonPath("$.data.content[0].thumbnailUrl").value(course.getThumbnailUrl()))
                .andExpect(jsonPath("$.data.content[0].comment").value(course.getComment()))
                .andExpect(jsonPath("$.data.content[0].level").value(course.getLevel()))
                .andExpect(jsonPath("$.data.content[0].race").value(course.getRace()))
                .andExpect(jsonPath("$.data.content[0].price").value(course.getPrice()))
                .andExpect(status().isOk());
    }

    @WithMemberInfo
    @DisplayName("강의 조회 성공")
    @Test
    void getMyCourse_success() throws Exception {

        Info info = Info.builder()
            .id(1L)
            .gamerName("test")
            .title("벙커링")
            .videoUrl("https://www.youtube.com/watch?v=2rpu0f-qog4")
            .thumbnailUrl("https://img.youtube.com/vi/2rpu0f-qog4/default.jpg")
            .comment("세상에서 제일 쉬운 8배럭 벙커링 강의")
            .level("입문")
            .race("테란")
            .price(10L)
            .build();


        //given
        given(courseService.searchMyCourse(anyLong(), anyLong()))
            .willReturn(info);

        //when
        //then
        mockMvc.perform(get("/courses/1"))
            .andDo(print())
            .andExpect(jsonPath("$.data.title").value(info.getTitle()))
            .andExpect(jsonPath("$.data.videoUrl").value(info.getVideoUrl()))
            .andExpect(jsonPath("$.data.thumbnailUrl").value(info.getThumbnailUrl()))
            .andExpect(jsonPath("$.data.comment").value(info.getComment()))
            .andExpect(jsonPath("$.data.level").value(info.getLevel()))
            .andExpect(jsonPath("$.data.race").value(info.getRace()))
            .andExpect(jsonPath("$.data.price").value(info.getPrice()))
            .andExpect(status().isOk());


    }
}