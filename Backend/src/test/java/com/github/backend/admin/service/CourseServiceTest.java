package com.github.backend.admin.service;

import com.github.backend.dto.course.CourseInfoOutputDto;
import com.github.backend.dto.course.SearchCourse;
import com.github.backend.exception.course.CourseException;
import com.github.backend.persist.course.Course;
import com.github.backend.persist.course.repository.querydsl.CourseSearchRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;


    @Mock
    private CourseSearchRepository courseSearchRepository;

    @InjectMocks
    private CourseService courseService;

    @Test
    @DisplayName("강의 정보 검색 성공")
    void testGetCourseInfo(){
        //given
        List<Course> courses = new ArrayList<>();
        Gamer gamer = Gamer.builder()
                .id(1L)
                .name("유영진")
                .build();

        Course course = Course.builder()
                .gamer(gamer)
                .title("벙커링")
                .videoUrl("https://www.youtube.com/watch?v=2rpu0f-qog4")
                .thumbnailUrl("https://img.youtube.com/vi/2rpu0f-qog4/default.jpg")
                .comment("세상에서 제일 쉬운 8배럭 벙커링 강의")
                .level("입문")
                .race("테란")
                .price(10L)
                .build();

        for (int i = 0; i < 2; i++) {
            courses.add(course);
        }
        given(courseSearchRepository.searchByWhere(any()))
                .willReturn(courses);

        SearchCourse searchCourse = SearchCourse.
                builder()
                .title(course.getTitle())
                .build();

        //when
        List<CourseInfoOutputDto.Info> courseList = courseService.searchCourseList(searchCourse);

        //then
        assertEquals(courseList.size(), 2);
        assertEquals(courseList.get(0).getTitle(), course.getTitle());
    }

}

