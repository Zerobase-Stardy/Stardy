package com.github.backend.admin.service;

import com.github.backend.exception.CourseException;
import com.github.backend.model.dto.UpdateCourse;
import com.github.backend.persist.entity.Course;
import com.github.backend.persist.entity.Gamer;
import com.github.backend.persist.repository.CourseRepository;
import com.github.backend.persist.repository.GamerRepository;
import com.github.backend.service.CourseService;
import com.github.backend.service.GamerService;
import com.github.backend.type.CourseErrorCode;
import com.github.backend.type.GamerErrorCode;
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

    @Mock
    private GamerRepository gamerRepository;

    @InjectMocks
    private CourseService courseService;

    @InjectMocks
    private GamerService gamerService;

    @Test
    @DisplayName("강의 등록 성공")
    void testRegisterCourse(){
        //given
        given(gamerRepository.save(any()))
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
                "유영진","테란","rush","단단한 테란"
        );

        given(gamerRepository.findById(anyLong()))
                .willReturn(Optional.of(gamer));

        given(courseRepository.existsByTitle(anyString()))
                .willReturn(false);

        given(courseRepository.save(any()))
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
                gamer.getId(),
                "벙커링",
                "https://www.youtube.com/watch?v=2rpu0f-qog4"
                ,"https://img.youtube.com/vi/2rpu0f-qog4/default.jpg"
                ,"세상에서 제일 쉬운 8배럭 벙커링 강의"
                ,"입문"
                ,"테란"
                ,10L
        );
        //then
        assertEquals(course.getTitle(), "벙커링");
        assertEquals(course.getVideoUrl(), "https://www.youtube.com/watch?v=2rpu0f-qog4");
        assertEquals(course.getThumbnailUrl(), "https://img.youtube.com/vi/2rpu0f-qog4/default.jpg");
        assertEquals(course.getComment(), "세상에서 제일 쉬운 8배럭 벙커링 강의");
        assertEquals(course.getLevel(), "입문");
        assertEquals(course.getRace(), "테란");
        assertEquals(course.getPrice(), 10L);
    }

    @Test
    @DisplayName("강의 등록 실패 - 게이머 없음")
    void testRegisterCourseFailedGamerNotFound(){
        //given
        given(gamerRepository.findById(anyLong()))
                .willReturn(Optional.empty());
        //when
        CourseException courseException = assertThrows(CourseException.class,
                ()-> courseService.registerCourse(
                        1L,
                        "벙커링",
                        "https://www.youtube.com/watch?v=2rpu0f-qog4"
                        ,"https://img.youtube.com/vi/2rpu0f-qog4/default.jpg"
                        ,"세상에서 제일 쉬운 8배럭 벙커링 강의"
                        ,"입문"
                        ,"테란"
                        ,10L
                ));
        //then
        assertEquals(courseException.getErrorCode(), CourseErrorCode.NOT_EXIST_GAMER);
    }
    @Test
    @DisplayName("강의 등록 실패 - 해당 하는 이름의 강좌명이 이미 존재.")
    void testRegisterCourseFailedExistsTitle(){
        //given
        given(gamerRepository.save(any()))
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
                "유영진","테란","rush","단단한 테란"
        );

        given(gamerRepository.findById(anyLong()))
                .willReturn(Optional.of(gamer));
        given(courseRepository.existsByTitle(anyString()))
                .willReturn(true);
        //when
        CourseException courseException = assertThrows(CourseException.class,
                ()-> courseService.registerCourse(
                        1L,
                        "벙커링",
                        "https://www.youtube.com/watch?v=2rpu0f-qog4"
                        ,"https://img.youtube.com/vi/2rpu0f-qog4/default.jpg"
                        ,"세상에서 제일 쉬운 8배럭 벙커링 강의"
                        ,"입문"
                        ,"테란"
                        ,10L
                ));
        //then
        assertEquals(courseException.getErrorCode(), CourseErrorCode.EXIST_SAME_TITLE);
    }

    @Test
    @DisplayName("강의 정보 조회 성공")
    void testGetCourseInfo(){
        //given
        given(gamerRepository.save(any()))
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
                "유영진","테란","rush","단단한 테란"
        );

        given(gamerRepository.findById(anyLong()))
                .willReturn(Optional.of(gamer));

        given(courseRepository.existsByTitle(anyString()))
                .willReturn(false);

        given(courseRepository.save(any()))
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
                gamer.getId(),
                "벙커링",
                "https://www.youtube.com/watch?v=2rpu0f-qog4"
                ,"https://img.youtube.com/vi/2rpu0f-qog4/default.jpg"
                ,"세상에서 제일 쉬운 8배럭 벙커링 강의"
                ,"입문"
                ,"테란"
                ,10L
        );

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

    @Test
    @DisplayName("강의 정보 수정")
    void testUpdateCourseInfo(){
        //given
        Gamer gamer = Gamer.builder()
                .id(1L)
                .name("유영진")
                .race("테란")
                .nickName("rush")
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

        Course updateCourse = Course.builder()
                .id(1L)
                .title("벙커링")
                .videoUrl("https://www.youtube.com/watch?v=2rpu0f-qog4")
                .thumbnailUrl("https://img.youtube.com/vi/2rpu0f-qog4/default.jpg")
                .comment("심화 8배럭 벙커링 강의")
                .level("고급")
                .race("테란")
                .price(20L)
                .build();

        given(gamerRepository.findById(anyLong()))
                .willReturn(Optional.of(gamer));

        given(courseRepository.findById(anyLong()))
                .willReturn(Optional.of(course));

        given(courseRepository.save(any()))
                .willReturn(updateCourse);

        UpdateCourse.Request request =
                new UpdateCourse.Request(
                        1L,
                        "벙커링",
                        "https://www.youtube.com/watch?v=2rpu0f-qog4",
                        "https://img.youtube.com/vi/2rpu0f-qog4/default.jpg",
                        "심화 8배럭 벙커링 강의",
                        "고급",
                        "테란",
                        20L
                );

        //when
        Course compCourse = courseService.updateCourseInfo(course.getId(), request);

        //then
        assertEquals(updateCourse.getId(), compCourse.getId());
        assertEquals(updateCourse.getTitle(), compCourse.getTitle());
        assertEquals(updateCourse.getVideoUrl(), compCourse.getVideoUrl());
        assertEquals(updateCourse.getThumbnailUrl(), compCourse.getThumbnailUrl());
        assertEquals(updateCourse.getComment(), compCourse.getComment());
        assertEquals(updateCourse.getLevel(), compCourse.getLevel());
        assertEquals(updateCourse.getRace(), compCourse.getRace());
        assertEquals(updateCourse.getGamer(), compCourse.getGamer());
    }

    @Test
    @DisplayName("강의 정보 수정 실패 - 해당하는 게이머 없음")
    void testUpdateCourseInfoFailedNotFoundGamer(){
        //given
        given(gamerRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        UpdateCourse.Request request =
                new UpdateCourse.Request(
                        1L,
                        "벙커링",
                        "https://www.youtube.com/watch?v=2rpu0f-qog4",
                        "https://img.youtube.com/vi/2rpu0f-qog4/default.jpg",
                        "심화 8배럭 벙커링 강의",
                        "고급",
                        "테란",
                        20L
                );

        //when
        CourseException courseException = assertThrows(CourseException.class,
                () -> courseService.updateCourseInfo(1L, request));
        //then
        assertEquals(courseException.getErrorCode(), CourseErrorCode.NOT_EXIST_GAMER);
    }

    @Test
    @DisplayName("강의 정보 수정 실패 - 해당하는 강의 없음")
    void testUpdateCourseInfoFailedNotFoundCourse(){
        //given
        Gamer gamer = Gamer.builder()
                .id(1L)
                .name("유영진")
                .race("테란")
                .nickName("rush")
                .introduce("단단한 테란")
                .build();
        given(gamerRepository.findById(anyLong()))
                .willReturn(Optional.of(gamer));

        UpdateCourse.Request request =
                new UpdateCourse.Request(
                        1L,
                        "벙커링",
                        "https://www.youtube.com/watch?v=2rpu0f-qog4",
                        "https://img.youtube.com/vi/2rpu0f-qog4/default.jpg",
                        "심화 8배럭 벙커링 강의",
                        "고급",
                        "테란",
                        20L
                );
        given(courseRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        //when
        CourseException courseException = assertThrows(CourseException.class,
                () -> courseService.updateCourseInfo(1L, request));
        //then
        assertEquals(courseException.getErrorCode(), CourseErrorCode.NOT_EXIST_COURSE);
    }
}

