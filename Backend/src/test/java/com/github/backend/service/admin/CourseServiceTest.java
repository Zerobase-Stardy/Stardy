package com.github.backend.service.admin;

import com.github.backend.dto.course.CourseInfoOutputDto;
import com.github.backend.dto.course.CourseInfoOutputDto.Info;
import com.github.backend.dto.course.SearchCourse;
import com.github.backend.persist.course.Course;
import com.github.backend.persist.course.repository.querydsl.CourseSearchRepository;
import com.github.backend.persist.gamer.Gamer;
import com.github.backend.persist.course.repository.CourseRepository;
import com.github.backend.persist.myCourse.MyCourse;
import com.github.backend.persist.myCourse.repository.MyCourseRepository;
import com.github.backend.service.course.impl.CourseService;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;


    @Mock
    private CourseSearchRepository courseSearchRepository;

    @Mock
    private MyCourseRepository myCourseRepository;

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

        courses.add(course);
        courses.add(course2);


        given(courseSearchRepository.searchByWhere(any(), any()))
                .willReturn(new PageImpl<>(courses));

        SearchCourse searchCourse = SearchCourse.
                builder()
                .title(course.getTitle())
                .build();

        Pageable pageable = PageRequest.of(1, 2, Sort.Direction.DESC, "id");

        //when
        Page<CourseInfoOutputDto.Info> courseList = courseService.searchCourseList(searchCourse, pageable);


        //then
        assertEquals(courseList.getContent().get(0).getRace(), course.getRace());
        assertEquals(courseList.getContent().get(1).getRace(), course2.getRace());
    }

    @DisplayName("강의 조회 성공")
    @Test
    void searchMyCourse_success(){
        //given
        Course course = Course.builder()
            .id(2L)
            .gamer(Gamer.builder()
                .id(1L)
                .name("유영진")
                .build())
            .title("벙커링")
            .price(10L)
            .build();

        MyCourse myCourse = MyCourse.builder()
            .course(course)
            .build();

        given(myCourseRepository.findByMember_IdAndCourse_Id(anyLong(), any()))
            .willReturn(Optional.of(myCourse));

        //when
        Info info = courseService.searchMyCourse(anyLong(), anyLong());

        //then
        assertThat(info.getId()).isEqualTo(course.getId());
        assertThat(info.getTitle()).isEqualTo(course.getTitle());
        assertThat(info.getPrice()).isEqualTo(course.getPrice());

    }
}

