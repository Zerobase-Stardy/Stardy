package com.github.backend.admin.service;

import com.github.backend.dto.admin.RegisterAdminOutputDto;
import com.github.backend.dto.course.SearchCourse;
import com.github.backend.dto.member.MemberSearchOutputDto;
import com.github.backend.dto.member.SearchMember;
import com.github.backend.persist.common.type.AuthType;
import com.github.backend.persist.member.Member;
import com.github.backend.persist.member.repository.MemberRepository;
import com.github.backend.persist.member.repository.MemberSearchRepository;
import com.github.backend.persist.member.type.MemberStatus;
import com.github.backend.security.jwt.Tokens;
import com.github.backend.dto.course.CourseInfoOutputDto;
import com.github.backend.dto.course.RegisterCourse;
import com.github.backend.dto.gamer.*;
import com.github.backend.exception.admin.AdminException;
import com.github.backend.exception.admin.code.AdminErrorCode;
import com.github.backend.exception.course.CourseException;
import com.github.backend.exception.course.code.CourseErrorCode;
import com.github.backend.exception.gamer.GamerErrorCode;
import com.github.backend.exception.gamer.GamerException;
import com.github.backend.persist.gamer.Gamer;
import com.github.backend.persist.gamer.repository.GamerRepository;
import com.github.backend.persist.gamer.repository.querydsl.GamerSearchRepository;
import com.github.backend.persist.member.type.Role;
import com.github.backend.dto.course.UpdateCourse;
import com.github.backend.persist.admin.Admin;
import com.github.backend.persist.admin.repository.AdminRepository;
import com.github.backend.persist.course.Course;
import com.github.backend.persist.course.repository.CourseRepository;
import com.github.backend.persist.course.repository.querydsl.CourseSearchRepository;
import com.github.backend.service.admin.impl.AdminService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.github.backend.security.jwt.TokenService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    private MemberSearchRepository memberSearchRepository;

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private AdminService adminService;

    @Test
    @DisplayName("관리자 계정 생성 성공")
    void testCreateAdmin(){
        //given
        Admin admin = Admin.builder()
                .adminId("admin1234")
                .password("password")
                .role(Role.ROLE_ADMIN)
                .build();

        given(adminRepository.save(any()))
                .willReturn(admin);

        //when
        RegisterAdminOutputDto.Info adminInfo = adminService.registerAdmin(
                "admin1234","password"
        );

        //then
        assertEquals(adminInfo.getAdminId(), admin.getAdminId());
        assertEquals(adminInfo.getPassword(), admin.getPassword());
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
        Gamer gamer = Gamer.builder()
                .name("유영진")
                .race("테란")
                .nickname("rush")
                .introduce("단단한 테란")
                .build();
        given(gamerRepository.save(any()))
                .willReturn(gamer);
        //when

        RegisterGamerOutputDto.Info gamerInfo = adminService.registerGamer(
                RegisterGamer.Request.builder()
                        .name(gamer.getName())
                        .race(gamer.getRace())
                        .nickname(gamer.getNickname())
                        .introduce(gamer.getIntroduce())
                        .build()
        );

        //then
        assertEquals(gamerInfo.getName(), gamer.getName());
        assertEquals(gamerInfo.getRace(), gamer.getRace());
        assertEquals(gamerInfo.getNickname(), gamer.getNickname());
        assertEquals(gamerInfo.getIntroduce(), gamer.getIntroduce());
    }

    @Test
    @DisplayName("게이머 등록 실패 - 중복 사용자")
    void testRegisterGamerFailed(){
        //given
        given(gamerRepository.existsByNickname(anyString()))
                .willReturn(true);
        //when
        GamerException gamerException = assertThrows(GamerException.class,
                () -> adminService.registerGamer(
                        RegisterGamer.Request.builder()
                                .nickname("rush")
                                .build()
                )
        );

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

        GamerInfoOutputDto.Info gamerInfo = adminService.getGamerInfo(gamer.getId());

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
        List<GamerInfoOutputDto.Info> findGamerList = adminService.getGamerList(searchGamer);

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
        List<GamerInfoOutputDto.Info> gamerNameList = adminService.getGamerList(searchGamer);

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
        List<GamerInfoOutputDto.Info> gamerRaceList = adminService.getGamerList(searchGamer);

        //then
        assertEquals(gamerRaceList.size(), 2);
        assertEquals(gamerRaceList.get(0).getRace(), gamer.getRace());
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
        List<GamerInfoOutputDto.Info> gamerNicknameList = adminService.getGamerList(searchGamer);

        //then
        assertEquals(gamerNicknameList.size(), 1);
        assertEquals(gamerNicknameList.get(0).getNickname(), gamer.getNickname());
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

        GamerInfoOutputDto.Info gamerInfo = adminService.updateGamer(
                gamer.getId(),
                UpdateGamer.Request.builder()
                        .name(updateGamer.getName())
                        .race(updateGamer.getRace())
                        .nickname(updateGamer.getNickname())
                        .introduce(updateGamer.getIntroduce())
                        .build()
        );

        //then
        assertEquals(gamerInfo.getName(), updateGamer.getName());
        assertEquals(gamerInfo.getRace(), updateGamer.getRace());
        assertEquals(gamerInfo.getNickname(), updateGamer.getNickname());
        assertEquals(gamerInfo.getIntroduce(), updateGamer.getIntroduce());
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
                        UpdateGamer.Request.builder().build()
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
        GamerInfoOutputDto.Info gamerInfo = adminService.deleteGamer(gamer.getId());

        //then
        verify(gamerRepository).delete(gamer);
        assertEquals(gamerInfo.getId(), gamer.getId());
        assertEquals(gamerInfo.getName(), gamer.getName());
        assertEquals(gamerInfo.getRace(), gamer.getRace());
        assertEquals(gamerInfo.getNickname(), gamer.getNickname());
        assertEquals(gamerInfo.getIntroduce(), gamer.getIntroduce());
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
        Gamer gamer = Gamer.builder()
                .id(1L)
                .name("유영진")
                .race("테란")
                .nickname("rush")
                .introduce("단단한 테란")
                .build();

        given(gamerRepository.findById(anyLong()))
                .willReturn(Optional.of(gamer));

        given(courseRepository.existsByTitle(anyString()))
                .willReturn(false);

        Course course =  Course.builder()
                .gamer(gamer)
                .title("벙커링")
                .videoUrl("https://www.youtube.com/watch?v=2rpu0f-qog4")
                .thumbnailUrl("https://img.youtube.com/vi/2rpu0f-qog4/default.jpg")
                .comment("세상에서 제일 쉬운 8배럭 벙커링 강의")
                .level("입문")
                .race("테란")
                .price(10L)
                .build();

        given(courseRepository.save(any()))
                .willReturn(course);
        //when
        CourseInfoOutputDto.Info courseInfo = adminService.registerCourse(
                RegisterCourse.Request.builder()
                        .gamerId(course.getGamer().getId())
                        .title(course.getTitle())
                        .videoUrl(course.getVideoUrl())
                        .thumbnailUrl(course.getThumbnailUrl())
                        .comment(course.getComment())
                        .level(course.getLevel())
                        .race(course.getRace())
                        .price(course.getPrice())
                        .build()
        );
        //then
        assertEquals(courseInfo.getTitle(), course.getTitle());
        assertEquals(courseInfo.getVideoUrl(), course.getVideoUrl());
        assertEquals(courseInfo.getThumbnailUrl(), course.getThumbnailUrl());
        assertEquals(courseInfo.getComment(), course.getComment());
        assertEquals(courseInfo.getLevel(), course.getLevel());
        assertEquals(courseInfo.getRace(), course.getRace());
        assertEquals(courseInfo.getPrice(), course.getPrice());

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
                        RegisterCourse.Request.builder()
                                .gamerId(1L)
                                .build()
                ));
        //then
        assertEquals(courseException.getErrorCode(), CourseErrorCode.NOT_EXIST_GAMER);
    }
    @Test
    @DisplayName("강의 등록 실패 - 해당 하는 이름의 강좌명이 이미 존재.")
    void testRegisterCourseFailedExistsTitle(){
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
        given(courseRepository.existsByTitle(anyString()))
                .willReturn(true);
        //when
        CourseException courseException = assertThrows(CourseException.class,
                ()-> adminService.registerCourse(
                        RegisterCourse.Request.builder()
                                .gamerId(gamer.getId())
                                .title("")
                                .build()
                ));
        //then
        assertEquals(courseException.getErrorCode(), CourseErrorCode.EXIST_SAME_TITLE);
    }

    @Test
    @DisplayName("강의 제목 검색 조회 성공")
    void testGetCourseTitleList(){
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
        List<CourseInfoOutputDto.Info> courseList = adminService.searchCourseList(searchCourse);

        //then
        assertEquals(courseList.size(), 2);
        assertEquals(courseList.get(0).getTitle(), course.getTitle());
    }

    @Test
    @DisplayName("강의 level 조회 성공")
    void testGetCourseLevelList(){
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
                .level(course.getLevel())
                .build();

        //when
        List<CourseInfoOutputDto.Info> courseList = adminService.searchCourseList(searchCourse);

        //then
        assertEquals(courseList.size(), 2);
        assertEquals(courseList.get(0).getLevel(), course.getLevel());
    }


    @Test
    @DisplayName("강의 종족 조회 성공")
    void testGetCourseRaceList(){
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
                .race(course.getRace())
                .build();

        //when
        List<CourseInfoOutputDto.Info> courseList = adminService.searchCourseList(searchCourse);

        //then
        assertEquals(courseList.size(), 2);
        assertEquals(courseList.get(0).getRace(), course.getRace());
    }

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
        CourseInfoOutputDto.Info courseInfo = adminService.getCourseInfo(course.getId());

        //then
        assertEquals(course.getId(), courseInfo.getId());
        assertEquals(course.getGamer().getName(), courseInfo.getGamerName());
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
                .gamer(gamer)
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
                 UpdateCourse.Request.builder()
                         .gamerId(gamer.getId())
                         .title(updateCourse.getTitle())
                         .videoUrl(updateCourse.getVideoUrl())
                         .comment(updateCourse.getComment())
                         .level(updateCourse.getLevel())
                         .race(updateCourse.getRace())
                         .price(updateCourse.getPrice())
                         .build();

        //when
        CourseInfoOutputDto.Info updateCourseInfo = adminService.updateCourseInfo(course.getId(), request);

        //then
        assertEquals(updateCourse.getId(), updateCourseInfo.getId());
        assertEquals(updateCourse.getTitle(), updateCourseInfo.getTitle());
        assertEquals(updateCourse.getVideoUrl(), updateCourseInfo.getVideoUrl());
        assertEquals(updateCourse.getThumbnailUrl(), updateCourseInfo.getThumbnailUrl());
        assertEquals(updateCourse.getComment(), updateCourseInfo.getComment());
        assertEquals(updateCourse.getLevel(), updateCourseInfo.getLevel());
        assertEquals(updateCourse.getRace(), updateCourseInfo.getRace());
        assertEquals(updateCourse.getGamer().getName(), updateCourseInfo.getGamerName());
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
        Gamer gamer = Gamer.builder()
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

        given(courseRepository.findById(anyLong()))
                .willReturn(Optional.of(course));

        //when
        CourseInfoOutputDto.Info courseInfo = adminService.deleteCourse(course.getId());

        //then
        verify(courseRepository).delete(course);
        assertEquals(courseInfo.getId(), course.getId());
    }

    @Test
    @DisplayName("Admin 로그인 성공")
    void testLoginAdmin(){
        //given
        Admin admin = Admin.builder()
                .adminId("admin")
                .password("password")
                .role(Role.ROLE_ADMIN)
                .build();

        given(adminRepository.findByAdminId(anyString()))
                .willReturn(Optional.of(admin));

        Tokens tokens = Tokens.builder()
                .accessToken("access")
                .refreshToken("refresh")
                .build();

        given(tokenService.issueAllToken(any()))
                .willReturn(tokens);

        //when
        Tokens compTokens = adminService.loginAdmin(admin.getAdminId(), admin.getPassword());

        //then
        assertEquals(compTokens.getAccessToken(), tokens.getAccessToken());
        assertEquals(compTokens.getRefreshToken(), tokens.getRefreshToken());
    }


    @Test
    @DisplayName("어드민 로그인 실패 - 아이디를 찾을 수 없음")
    void testLoginAdminFailedNotFoundId(){
        //given
        given(adminRepository.findByAdminId(anyString()))
                .willReturn(Optional.empty());
        //when
        AdminException adminException = assertThrows(AdminException.class,
                () -> adminService.loginAdmin("", ""));

        //then
        assertEquals(adminException.getErrorCode(), AdminErrorCode.NOT_EXIST_ADMIN_ID);
    }

    @Test
    @DisplayName("어드민 로그인 실패 - 패스워드를 찾을 수 없음")
    void testLoginAdminFailedNotFoundPassword(){
        //given
        Admin admin = Admin.builder()
                .adminId("admin")
                .password("1234")
                .role(Role.ROLE_ADMIN)
                .build();

        given(adminRepository.findByAdminId(anyString()))
                .willReturn(Optional.of(admin));

        //when
        AdminException adminException = assertThrows(AdminException.class,
                () -> adminService.loginAdmin(admin.getAdminId(), ""));

        //then
        assertEquals(adminException.getErrorCode(), AdminErrorCode.PASSWORD_IS_WRONG);
    }

    @Test
    @DisplayName("멤버 조회 성공")
    void testGetMemberList(){
        //given
        List<Member> members = new ArrayList<>();
        Member member = Member.builder()
                .email("email@kakao.com")
                .nickname("nick")
                .status(MemberStatus.PERMITTED)
                .authType(AuthType.KAKAO)
                .role(Role.ROLE_USER)
                .point(100)
                .build();

        for (int i = 0; i < 2; i++) {
            members.add(member);
        }
        given(memberSearchRepository.searchByWhere(any()))
                .willReturn(members);


        //when
        List<MemberSearchOutputDto.Info> memberList = adminService.searchMemberList(
                SearchMember.builder()
                .email(member.getEmail())
                .nickname(member.getNickname())
                .point(member.getPoint())
                .build()
        );

        //then
        assertEquals(memberList.size(), 2);
        assertEquals(memberList.get(0).getEmail(), member.getEmail());
        assertEquals(memberList.get(0).getNickname(), member.getNickname());
        assertEquals(memberList.get(0).getPoint(), member.getPoint());
    }


    @Test
    @DisplayName("멤버 정보 수정 - nickname")
    void testUpdateMemberNickname(){
        //given
        Member member = Member.builder()
                .id(1L)
                .email("email@kakao.com")
                .nickname("nick")
                .status(MemberStatus.PERMITTED)
                .authType(AuthType.KAKAO)
                .role(Role.ROLE_USER)
                .point(100)
                .build();

        Member updateMember = Member.builder()
                .nickname("modify")
                .build();

        given(memberRepository.findById(anyLong()))
                .willReturn(Optional.of(member));

        //when
        MemberSearchOutputDto.Info memberInfo = adminService.memberNicknameChange(
                member.getId(),
                updateMember.getNickname()
        );

        //then
        assertEquals(memberInfo.getNickname(), updateMember.getNickname());
    }




}
