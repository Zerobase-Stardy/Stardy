package com.github.backend.admin.service;

import com.github.backend.exception.course.CourseException;
import com.github.backend.persist.course.Course;
import com.github.backend.persist.gamer.Gamer;
import com.github.backend.persist.course.repository.CourseRepository;
import com.github.backend.service.course.impl.CourseService;
import com.github.backend.exception.course.code.CourseErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseService courseService;

    @Test
    @DisplayName("강의 정보 조회 성공")
    void testGetCourseInfo(){
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

        given(courseRepository.findById(anyLong()))
                .willReturn(Optional.of(course));
        //when
        Course courseInfo = courseService.getCourseInfo(course.getId());

        //then
        assertEquals(course.getId(), courseInfo.getId());
        assertEquals(course.getGamer().getName(), courseInfo.getGamer().getName());
        assertEquals(course.getTitle(), courseInfo.getTitle());
        assertEquals(course.getVideoUrl(), courseInfo.getVideoUrl());
        assertEquals(course.getThumbnailUrl(), courseInfo.getThumbnailUrl());
        assertEquals(course.getComment(), courseInfo.getComment());
        assertEquals(course.getRace(), courseInfo.getRace());
        assertEquals(course.getPrice(), courseInfo.getPrice());
    }

    @Test
    @DisplayName("강의 정보 조회 실패 - 해당하는 강의 없음")
    void testGetCourseInfoFailed(){
        //given
        given(courseRepository.findById(anyLong()))
                .willReturn(Optional.empty());
        //when
        CourseException courseException = assertThrows(CourseException.class,
                () -> courseService.getCourseInfo(1L));
        //then
        assertEquals(courseException.getErrorCode(), CourseErrorCode.NOT_EXIST_COURSE);
    }

}

