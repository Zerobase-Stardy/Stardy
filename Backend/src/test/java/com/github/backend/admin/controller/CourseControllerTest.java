package com.github.backend.admin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.backend.model.dto.RegisterCourse;
import com.github.backend.persist.entity.Course;
import com.github.backend.persist.entity.Gamer;
import com.github.backend.service.CourseService;
import com.github.backend.service.GamerService;
import com.github.backend.web.CourseController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(value = CourseController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class CourseControllerTest {

    @MockBean
    private CourseService courseService;

    @MockBean
    private GamerService gamerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("강의 등록 성공")
    void testRegisterCourse() throws Exception{
        //given
        given(gamerService.registerGamer(
                anyString(),
                anyString(),
                anyString(),
                anyString()))
                .willReturn(
                        Gamer.builder()
                                .id(1L)
                                .name("유영진")
                                .race("테란")
                                .nickName("rush")
                                .introduce("단단한 테란")
                                .build()
                );

        Gamer gamer = gamerService.registerGamer(
                "유영진",
                "테란",
                "rush",
                "단단한 테란"
        );

        given(courseService.registerCourse(
                anyLong(),
                anyString(),
                anyString(),
                anyString(),
                anyString(),
                anyString(),
                anyString(),
                anyLong()))
                .willReturn(
                        Course.builder()
                                .gamer(gamer)
                                .title("벙커링")
                                .videoUrl("https://www.youtube.com/watch?v=2rpu0f-qog4")
                                .thumbnailUrl("https://img.youtube.com/vi/2rpu0f-qog4/default.jpg")
                                .comment("세상에서 제일 쉬운 8배럭 벙커링 강의")
                                .level("입문")
                                .race("테란")
                                .price(10L)
                                .build()
                );
        //when
        Course course = courseService.registerCourse(
                1L,
                "벙커링",
                "https://www.youtube.com/watch?v=2rpu0f-qog4",
                "https://img.youtube.com/vi/2rpu0f-qog4/default.jpg",
                "세상에서 제일 쉬운 8배럭 벙커링 강의",
                "입문",
                "테란",
                10L
        );
        //then
        mockMvc.perform(post("/course/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new RegisterCourse.Request(
                                        course.getGamer().getId(),
                                        course.getTitle(),
                                        course.getVideoUrl(),
                                        course.getThumbnailUrl(),
                                        course.getComment(),
                                        course.getLevel(),
                                        course.getRace(),
                                        course.getPrice()
                                )
                        ))
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gamerName").value(course.getGamer().getName()))
                .andExpect(jsonPath("$.title").value(course.getTitle()))
                .andExpect(jsonPath("$.race").value(course.getRace()))
                .andExpect(jsonPath("$.level").value(course.getLevel()))
                .andExpect(jsonPath("$.comment").value(course.getComment()))
                .andExpect(jsonPath("$.price").value(course.getPrice()));
    }
    
    @Test
    @DisplayName("강의 정보 조회 성공")
    void testGetCourseInfo() throws Exception{
        //given
        given(gamerService.registerGamer(
                anyString(),
                anyString(),
                anyString(),
                anyString()))
                .willReturn(
                        Gamer.builder()
                                .id(1L)
                                .name("유영진")
                                .race("테란")
                                .nickName("rush")
                                .introduce("단단한 테란")
                                .build()
                );

        Gamer gamer = gamerService.registerGamer(
                "유영진",
                "테란",
                "rush",
                "단단한 테란"
        );

        given(courseService.registerCourse(
                anyLong(),
                anyString(),
                anyString(),
                anyString(),
                anyString(),
                anyString(),
                anyString(),
                anyLong()))
                .willReturn(
                        Course.builder()
                                .id(1L)
                                .gamer(gamer)
                                .title("벙커링")
                                .videoUrl("https://www.youtube.com/watch?v=2rpu0f-qog4")
                                .thumbnailUrl("https://img.youtube.com/vi/2rpu0f-qog4/default.jpg")
                                .comment("세상에서 제일 쉬운 8배럭 벙커링 강의")
                                .level("입문")
                                .race("테란")
                                .price(10L)
                                .build()
                );
        Course course = courseService.registerCourse(
                1L,
                "벙커링",
                "https://www.youtube.com/watch?v=2rpu0f-qog4",
                "https://img.youtube.com/vi/2rpu0f-qog4/default.jpg",
                "세상에서 제일 쉬운 8배럭 벙커링 강의",
                "입문",
                "테란",
                10L
        );

        given(courseService.getCourseInfo(anyLong()))
                .willReturn(
                        Course.builder()
                                .id(1L)
                                .gamer(gamer)
                                .title("벙커링")
                                .videoUrl("https://www.youtube.com/watch?v=2rpu0f-qog4")
                                .thumbnailUrl("https://img.youtube.com/vi/2rpu0f-qog4/default.jpg")
                                .comment("세상에서 제일 쉬운 8배럭 벙커링 강의")
                                .level("입문")
                                .race("테란")
                                .price(10L)
                                .build()
                );
        //when
        Course courseInfo = courseService.getCourseInfo(1L);

        
        //then
        mockMvc.perform(get(String.format("/course/%d", 1)))
                .andDo(print())
                .andExpect(jsonPath("$.courseId").value(course.getId()))
                .andExpect(jsonPath("$.gamerName").value(course.getGamer().getName()))
                .andExpect(jsonPath("$.title").value(course.getTitle()))
                .andExpect(jsonPath("$.videoUrl").value(course.getVideoUrl()))
                .andExpect(jsonPath("$.thumbnailUrl").value(course.getThumbnailUrl()))
                .andExpect(jsonPath("$.comment").value(course.getComment()))
                .andExpect(jsonPath("$.level").value(course.getLevel()))
                .andExpect(jsonPath("$.race").value(course.getRace()))
                .andExpect(jsonPath("$.price").value(course.getPrice()))
                .andExpect(status().isOk());
    }

}
