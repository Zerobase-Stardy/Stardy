package com.github.backend.admin.service;

import com.github.backend.exception.AdminException;
import com.github.backend.exception.CourseException;
import com.github.backend.exception.GamerException;
import com.github.backend.model.constants.Role;
import com.github.backend.model.dto.SearchCourse;
import com.github.backend.model.dto.SearchGamer;
import com.github.backend.model.dto.UpdateCourse;
import com.github.backend.persist.entity.Admin;
import com.github.backend.persist.entity.Course;
import com.github.backend.persist.entity.Gamer;
import com.github.backend.persist.repository.AdminRepository;
import com.github.backend.persist.repository.CourseRepository;
import com.github.backend.persist.repository.GamerRepository;
import com.github.backend.persist.repository.querydsl.CourseSearchRepository;
import com.github.backend.persist.repository.querydsl.GamerSearchRepository;
import com.github.backend.service.AdminService;
import com.github.backend.type.AdminErrorCode;
import com.github.backend.type.CourseErrorCode;
import com.github.backend.type.GamerErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private GamerRepository gamerRepository;

    @Mock
    private GamerSearchRepository gamerSearchRepository;

    @Mock
    private CourseSearchRepository courseSearchRepository;

    @Mock
    private AdminRepository adminRepository;

    @InjectMocks
    private AdminService adminService;

    @Test
    @DisplayName("관리자 계정 생성 성공")
    void testCreateAdmin(){
        //given
        given(adminRepository.save(any()))
                .willReturn(
                        Admin.builder()
                                .adminId("admin1234")
                                .password("password")
                                .role(Role.ROLE_ADMIN)
                                .build()
                );
        //when
        Admin compareAdmin = adminService.registerAdmin(
                "admin1234","password"
        );

        //then
        assertEquals(compareAdmin.getAdminId(), "admin1234");
        assertEquals(compareAdmin.getPassword(), "password");
        assertEquals(compareAdmin.getRole(), Role.ROLE_ADMIN);
    }

    @Test
    @DisplayName("관리자 계정 생성 실패 - 중복 사용자")
    void testCreateAdminFailed(){
        //given
        given(adminRepository.existsByAdminId(anyString()))
                .willReturn(true);
        //when
        AdminException adminException = assertThrows(AdminException.class,
                () -> adminService.registerAdmin("admin1234","password"));

        //then
        assertEquals(AdminErrorCode.EXIST_SAME_ADMIN_ID.getDescription(), adminException.getErrorCode().getDescription());
    }
    @Test
    @DisplayName("게이머 등록 성공")
    void testRegisterGamer(){
        //given
        given(gamerRepository.save(any()))
                .willReturn(
                        Gamer.builder()
                                .name("유영진")
                                .race("테란")
                                .nickname("rush")
                                .introduce("단단한 테란")
                                .build()
                );
        //when

        Gamer gamer = adminService.registerGamer(
                "유영진","테란","rush","단단한 테란"
        );
        //then
        assertEquals(gamer.getName(), "유영진");
        assertEquals(gamer.getRace(), "테란");
        assertEquals(gamer.getNickname(), "rush");
        assertEquals(gamer.getIntroduce(), "단단한 테란");
    }

    @Test
    @DisplayName("게이머 등록 실패 - 중복 사용자")
    void testRegisterGamerFailed(){
        //given
        given(gamerRepository.existsByNickname(anyString()))
                .willReturn(true);
        //when
        GamerException gamerException = assertThrows(GamerException.class,
                () -> adminService.registerGamer("유영진","테란","rush","단단한 테란"));

        //then
        assertEquals(gamerException.getErrorCode(), GamerErrorCode.EXIST_SAME_GAMER_NICKNAME);
    }

    @Test
    @DisplayName("게이머 정보 상세 조회 성공")
    void testGetGamerInfo(){
        //given
        Gamer gamer = Gamer.builder()
                .id(1L)
                .name("유영진")
                .race("테란")
                .nickname("rush")
                .introduce("단단한 테란")
                .build();

        given(gamerRepository.findById(anyLong()))
                .willReturn(Optional.of(gamer));

        Gamer gamerInfo = adminService.getGamerInfo(gamer.getId());

        //then
        assertEquals(gamerInfo.getId(), gamer.getId());
        assertEquals(gamerInfo.getName(), gamer.getName());
        assertEquals(gamerInfo.getRace(), gamer.getRace());
        assertEquals(gamerInfo.getNickname(), gamer.getNickname());
        assertEquals(gamerInfo.getIntroduce(), gamer.getIntroduce());
    }

    @Test
    @DisplayName("게이머 정보 상세 조회 실패")
    void testGetGamerInfoFailed(){
        //given
        given(gamerRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        GamerException gamerException = assertThrows(
                GamerException.class,
                () -> adminService.getGamerInfo(1L)
        ) ;

        //then
        assertEquals(gamerException.getErrorCode(), GamerErrorCode.NOT_EXIST_GAMER);
    }

    @Test
    @DisplayName("게이머 목록 조회 성공")
    void testGetGamerList(){
        //given
        Gamer gamer = Gamer.builder()
                .name("유영진")
                .race("테란")
                .nickname("rush")
                .introduce("단단한 테란")
                .build();

        List<Gamer> gamers = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            gamers.add(gamer);
        }

        SearchGamer searchGamer = SearchGamer.builder()
                .name(gamer.getName())
                .race(gamer.getRace())
                .nickname(gamer.getNickname())
                .build();

        given(gamerSearchRepository.searchByWhere(any()))
                .willReturn(gamers);

        //when
        List<Gamer> findGamerList = adminService.getGamerList(searchGamer);

        //then
        assertEquals(2, findGamerList.size());
        assertEquals(gamer.getName(), findGamerList.get(0).getName());
        assertEquals(gamer.getNickname(), findGamerList.get(0).getNickname());
        assertEquals(gamer.getRace(), findGamerList.get(0).getRace());
        assertEquals(gamer.getIntroduce(), findGamerList.get(0).getIntroduce());
    }

    @Test
    @DisplayName("게이머 이름 검색 조회 성공")
    void testGetGamerNameList(){
        //given
        List<Gamer> gamers = new ArrayList<>();
        Gamer gamer = Gamer.builder()
                .name("유영진")
                .build();

        for (int i = 0; i < 2; i++) {
            gamers.add(gamer);
        }
        given(gamerSearchRepository.searchByWhere(any()))
                .willReturn(gamers);

        SearchGamer searchGamer = SearchGamer.builder()
                .name(gamer.getName())
                .build();

        //when
        List<Gamer> gamerNameList = adminService.getGamerList(searchGamer);

        //then
        assertEquals(gamerNameList.size(), 2);
        assertEquals(gamerNameList.get(0).getName(), gamer.getName());
    }

    @Test
    @DisplayName("게이머 종족 검색 조회 성공")
    void testGetGamerRaceList(){
        //given
        List<Gamer> gamers = new ArrayList<>();
        Gamer gamer = Gamer.builder()
                .race("테란")
                .build();

        for (int i = 0; i < 2; i++) {
            gamers.add(gamer);
        }
        SearchGamer searchGamer = SearchGamer.builder()
                .name(gamer.getName())
                .build();

        given(gamerSearchRepository.searchByWhere(any()))
                .willReturn(gamers);

        //when
        List<Gamer> gamerNameList = adminService.getGamerList(searchGamer);

        //then
        assertEquals(gamerNameList.size(), 2);
        assertEquals(gamerNameList.get(0).getRace(), gamer.getRace());
    }

    @Test
    @DisplayName("게이머 닉네임 검색 조회 성공")
    void testGetGamerNickNameList(){
        //given
        List<Gamer> gamers = new ArrayList<>();
        Gamer gamer = Gamer.builder()
                .nickname("rush")
                .build();

        gamers.add(gamer);
        SearchGamer searchGamer = SearchGamer.builder()
                .nickname(gamer.getNickname())
                .build();

        given(gamerSearchRepository.searchByWhere(any()))
                .willReturn(gamers);
        //when
        List<Gamer> gamerNameList = adminService.getGamerList(searchGamer);

        //then
        assertEquals(gamerNameList.size(), 1);
        assertEquals(gamerNameList.get(0).getNickname(), gamer.getNickname());
    }



    @Test
    @DisplayName("게이머 정보 수정 성공")
    void testUpdateGamer(){
        //given
        Gamer gamer = Gamer.builder()
                .id(1L)
                .name("유영진")
                .race("테란")
                .nickname("rush")
                .introduce("단단한 테란")
                .build();

        Gamer updateGamer = Gamer.builder()
                .id(1L)
                .name("유영진")
                .race("토스")
                .nickname("rush")
                .introduce("단단한 테란")
                .build();

        given(gamerRepository.findById(anyLong()))
                .willReturn(Optional.of(gamer));

        given(gamerRepository.save(any()))
                .willReturn(updateGamer);
        //when

        Gamer compGamer = adminService.updateGamer(
                1L, "유영진", "토스", "rush", "단단한 테란"
        );

        //then
        assertEquals(compGamer.getName(), updateGamer.getName());
        assertEquals(compGamer.getRace(), updateGamer.getRace());
        assertEquals(compGamer.getNickname(), updateGamer.getNickname());
        assertEquals(compGamer.getIntroduce(), updateGamer.getIntroduce());
    }


    @Test
    @DisplayName("게이머 정보 수정 실패 - 해당하는 게이머를 찾을 수 없음")
    void testUpdateGamerFailedNotFoundGamer(){
        //given

        given(gamerRepository.findById(anyLong()))
                .willReturn(Optional.empty());
        //when

        GamerException gamerException = assertThrows(
                GamerException.class, () -> adminService.updateGamer(
                        1L,
                        "유영진",
                        "테란",
                        "rush",
                        "단단한 테란"
                ));

        //then
        assertEquals(gamerException.getErrorCode(), GamerErrorCode.NOT_EXIST_GAMER);

    }

    @Test
    @DisplayName("게이머 정보 삭제 성공")
    void testDeleteGamerInfo(){
        //given
        Gamer gamer = Gamer.builder()
                .id(1L)
                .name("유영진")
                .race("테란")
                .nickname("rush")
                .introduce("단단한 테란")
                .build();

        given(gamerRepository.findById(gamer.getId()))
                .willReturn(Optional.of(gamer));

        //when
        adminService.deleteGamer(gamer.getId());

        //then
        verify(gamerRepository).delete(gamer);
    }

    @Test
    @DisplayName("게이머 정보 삭제 실패 - 게이머를 찾을 수 없음")
    void testDeleteGamerInfoFailedNotFoundGamer(){
        //given
        given(gamerRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        //when
        GamerException gamerException = assertThrows(
                GamerException.class, () -> adminService.deleteGamer(1L));
        //then
        assertEquals(gamerException.getErrorCode(), GamerErrorCode.NOT_EXIST_GAMER);
    }


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
                                .nickname("rush")
                                .introduce("단단한 테란")
                                .build()
                );
        Gamer gamer = adminService.registerGamer(
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
        Course course = adminService.registerCourse(
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
                ()-> adminService.registerCourse(
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
                                .nickname("rush")
                                .introduce("단단한 테란")
                                .build()
                );
        Gamer gamer = adminService.registerGamer(
                "유영진","테란","rush","단단한 테란"
        );

        given(gamerRepository.findById(anyLong()))
                .willReturn(Optional.of(gamer));
        given(courseRepository.existsByTitle(anyString()))
                .willReturn(true);
        //when
        CourseException courseException = assertThrows(CourseException.class,
                ()-> adminService.registerCourse(
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
    @DisplayName("강의 제목 검색 조회 성공")
    void testGetCourseTitleList(){
        //given
        List<Course> courses = new ArrayList<>();
        Course course = Course.builder()
                .title("단단한 벙커링")
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
        List<Course> courseList = adminService.searchCourseList(searchCourse);

        //then
        assertEquals(courseList.size(), 2);
        assertEquals(courseList.get(0).getTitle(), course.getTitle());
    }

    @Test
    @DisplayName("강의 level 조회 성공")
    void testGetCourseLevelList(){
        //given
        List<Course> courses = new ArrayList<>();
        Course course = Course.builder()
                .level("입문")
                .build();

        for (int i = 0; i < 2; i++) {
            courses.add(course);
        }
        given(courseSearchRepository.searchByWhere(any()))
                .willReturn(courses);

        SearchCourse searchCourse = SearchCourse.
                builder()
                .level(course.getLevel())
                .build();

        //when
        List<Course> courseList = adminService.searchCourseList(searchCourse);

        //then
        assertEquals(courseList.size(), 2);
        assertEquals(courseList.get(0).getLevel(), course.getLevel());
    }


    @Test
    @DisplayName("강의 종족 조회 성공")
    void testGetCourseRaceList(){
        //given
        List<Course> courses = new ArrayList<>();
        Course course = Course.builder()
                .race("테란")
                .build();

        for (int i = 0; i < 2; i++) {
            courses.add(course);
        }
        given(courseSearchRepository.searchByWhere(any()))
                .willReturn(courses);

        SearchCourse searchCourse = SearchCourse.
                builder()
                .race(course.getRace())
                .build();

        //when
        List<Course> courseList = adminService.searchCourseList(searchCourse);

        //then
        assertEquals(courseList.size(), 2);
        assertEquals(courseList.get(0).getRace(), course.getRace());
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
                                .nickname("rush")
                                .introduce("단단한 테란")
                                .build()
                );
        Gamer gamer = adminService.registerGamer(
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
        Course course = adminService.registerCourse(
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
        Course courseInfo = adminService.getCourseInfo(course.getId());

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
                () -> adminService.getCourseInfo(1L));
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
        Course compCourse = adminService.updateCourseInfo(course.getId(), request);

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
                () -> adminService.updateCourseInfo(1L, request));
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
                .nickname("rush")
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
                () -> adminService.updateCourseInfo(1L, request));
        //then
        assertEquals(courseException.getErrorCode(), CourseErrorCode.NOT_EXIST_COURSE);
    }

    @Test
    @DisplayName("강의 정보 삭제 성공")
    void testDeleteCourseInfo(){
        //given
        Course course = Course.builder()
                .id(1L)
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
        adminService.deleteCourse(course.getId());

        //then
        verify(courseRepository).delete(course);
    }
}
