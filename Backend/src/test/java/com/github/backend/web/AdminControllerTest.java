package com.github.backend.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.backend.config.SecurityConfig;
import com.github.backend.dto.admin.CreateAdmin;
import com.github.backend.dto.admin.LoginAdmin;
import com.github.backend.dto.admin.RegisterAdminOutputDto;
import com.github.backend.dto.member.MemberSearchOutputDto;
import com.github.backend.persist.common.type.AuthType;
import com.github.backend.persist.member.Member;
import com.github.backend.persist.member.type.MemberStatus;
import com.github.backend.security.jwt.Tokens;
import com.github.backend.dto.course.CourseInfoOutputDto;
import com.github.backend.dto.course.RegisterCourse;
import com.github.backend.dto.course.UpdateCourse;
import com.github.backend.dto.gamer.GamerInfoOutputDto;
import com.github.backend.dto.gamer.RegisterGamer;
import com.github.backend.dto.gamer.RegisterGamerOutputDto;
import com.github.backend.dto.gamer.UpdateGamer;
import com.github.backend.persist.admin.Admin;
import com.github.backend.persist.course.Course;
import com.github.backend.persist.gamer.Gamer;
import com.github.backend.persist.member.type.Role;
import com.github.backend.security.jwt.JwtAccessDeniedHandler;
import com.github.backend.security.jwt.JwtAuthenticationProvider;
import com.github.backend.security.jwt.JwtEntryPoint;
import com.github.backend.security.oauth.CustomOAuth2UserService;
import com.github.backend.security.oauth.OAuth2AuthenticationFailureHandler;
import com.github.backend.security.oauth.OAuth2AuthenticationSuccessHandler;
import com.github.backend.service.admin.impl.AdminService;
import com.github.backend.testUtils.WithMemberInfo;
import com.github.backend.web.admin.AdminController;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(value = AdminController.class,
        includeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)}
)
public class AdminControllerTest {

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
    private AdminService adminService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMemberInfo(role = "ROLE_ADMIN")
    @DisplayName("Admin 생성 성공")
    void testRegisterAdmin() throws Exception {
        //given
        Admin admin = Admin.builder()
                .adminId("admin")
                .password("password")
                .role(Role.ROLE_ADMIN)
                .build();
        given(adminService.registerAdmin(anyString(), anyString()))
                .willReturn(RegisterAdminOutputDto.Info.of(admin));
        //when

        //then
        mockMvc.perform(post("/admin-management/admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                CreateAdmin.Request.builder()
                                        .adminId(admin.getAdminId())
                                        .password(admin.getPassword())
                                        .build()
                        ))
                ).andDo(print())
                .andExpect(jsonPath("$.data.adminId").value(admin.getAdminId()))
                .andExpect(jsonPath("$.data.password").value(admin.getPassword()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMemberInfo(role = "ROLE_ADMIN")
    @DisplayName("Gamer 등록 성공")
    void testSuccessGamerRegister() throws Exception{
        //given
        Gamer gamer = Gamer.builder()
                .name("유영진")
                .race("테란")
                .nickname("rush")
                .introduce("단단한 테란")
                .build();

        given(adminService.registerGamer(any()))
                .willReturn(RegisterGamerOutputDto.Info.of(gamer));

        //when

        //then
        mockMvc.perform(post("/admin-management/gamer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                RegisterGamer.Request.builder()
                                        .name(gamer.getName())
                                        .race(gamer.getRace())
                                        .nickname(gamer.getNickname())
                                        .introduce(gamer.getIntroduce())
                                .build()
                        ))).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value(gamer.getName()))
                .andExpect(jsonPath("$.data.race").value(gamer.getRace()))
                .andExpect(jsonPath("$.data.nickname").value(gamer.getNickname()))
                .andExpect(jsonPath("$.data.introduce").value(gamer.getIntroduce()));
    }

    @Test
    @WithMemberInfo(role = "ROLE_ADMIN")
    @DisplayName("게이머 정보 수정 성공")
    void testUpdateGamerInfo() throws Exception{
        //given
        Gamer gamer = Gamer.builder()
                .id(1L)
                .name("유영진")
                .race("테란")
                .nickname("rush")
                .introduce("단단한 테란")
                .build();

        Gamer updateGamer = Gamer.builder()
                .id(gamer.getId())
                .name("유영사")
                .race("테란")
                .nickname("rush")
                .introduce("단단한 테란")
                .build();


        given(adminService.updateGamer(
                anyLong(), any()
        )).willReturn(GamerInfoOutputDto.Info.of(updateGamer));

        //when

        //then
        mockMvc.perform(put(String.format("/admin-management/gamer/%d", gamer.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                UpdateGamer.Request.builder()
                                        .name(gamer.getName())
                                        .race(gamer.getRace())
                                        .nickname(gamer.getNickname())
                                        .introduce(gamer.getIntroduce())
                                        .build()
                        )))
                .andDo(print())
                .andExpect(jsonPath("$.data.name").value(updateGamer.getName()))
                .andExpect(jsonPath("$.data.race").value(updateGamer.getRace()))
                .andExpect(jsonPath("$.data.nickname").value(updateGamer.getNickname()))
                .andExpect(jsonPath("$.data.introduce").value(updateGamer.getIntroduce()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMemberInfo(role = "ROLE_ADMIN")
    @DisplayName("게이머 상세 조회 성공")
    void testSuccessGetGamerInfo() throws Exception {
        //given
        Gamer gamer = Gamer.builder()
                .id(1L)
                .name("유영진")
                .race("테란")
                .nickname("rush")
                .introduce("단단한 테란")
                .build();


        given(adminService.getGamerInfo(anyLong()))
                .willReturn(GamerInfoOutputDto.Info.of(gamer));
        //when

        //then
        mockMvc.perform(get(String.format("/admin-management/gamer/%d",gamer.getId())))
                .andDo(print())
                .andExpect(jsonPath("$.data.name").value(gamer.getName()))
                .andExpect(jsonPath("$.data.race").value(gamer.getRace()))
                .andExpect(jsonPath("$.data.nickname").value(gamer.getNickname()))
                .andExpect(jsonPath("$.data.introduce").value(gamer.getIntroduce()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMemberInfo(role = "ROLE_ADMIN")
    @DisplayName("게이머 리스트 조회 성공")
    void testSuccessGetGamerList() throws Exception {
        //given
        Gamer gamer = Gamer.builder()
                .name("유영진")
                .race("테란")
                .nickname("rush")
                .introduce("단단한 테란")
                .build();
        List<GamerInfoOutputDto.Info> gamerList = new ArrayList<>();
        gamerList.add(GamerInfoOutputDto.Info.of(gamer));

        given(adminService.getGamerList(any()))
                .willReturn(gamerList);
        //when

        //then
        mockMvc.perform(get("/admin-management/gamers"))
                .andDo(print())
                .andExpect(jsonPath("$.data[0].name").value(gamer.getName()))
                .andExpect(jsonPath("$.data[0].race").value(gamer.getRace()))
                .andExpect(jsonPath("$.data[0].nickname").value(gamer.getNickname()))
                .andExpect(jsonPath("$.data[0].introduce").value(gamer.getIntroduce()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMemberInfo(role = "ROLE_ADMIN")
    @DisplayName("강의 등록 성공")
    void testRegisterCourse() throws Exception{
        //given
        Gamer gamer = Gamer.builder()
                .id(1L)
                .name("유영진")
                .race("테란")
                .nickname("rush")
                .introduce("단단한 테란")
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

        given(adminService.registerCourse(any()))
                .willReturn(CourseInfoOutputDto.Info.of(course));
        //when

        //then
        mockMvc.perform(post("/admin-management/course")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
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
                                )
                        )).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.gamerName").value(course.getGamer().getName()))
                .andExpect(jsonPath("$.data.title").value(course.getTitle()))
                .andExpect(jsonPath("$.data.race").value(course.getRace()))
                .andExpect(jsonPath("$.data.level").value(course.getLevel()))
                .andExpect(jsonPath("$.data.comment").value(course.getComment()))
                .andExpect(jsonPath("$.data.price").value(course.getPrice()));
    }

    @Test
    @WithMemberInfo(role = "ROLE_ADMIN")
    @DisplayName("강의 정보 조회 성공")
    void testGetCourseInfo() throws Exception{
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

        given(adminService.getCourseInfo(anyLong()))
                .willReturn(CourseInfoOutputDto.Info.of(course));
        //when

        //then
        mockMvc.perform(get(String.format("/admin-management/course/%d", course.getId())))
                .andDo(print())
                .andExpect(jsonPath("$.data.id").value(course.getId()))
                .andExpect(jsonPath("$.data.gamerName").value(course.getGamer().getName()))
                .andExpect(jsonPath("$.data.title").value(course.getTitle()))
                .andExpect(jsonPath("$.data.videoUrl").value(course.getVideoUrl()))
                .andExpect(jsonPath("$.data.thumbnailUrl").value(course.getThumbnailUrl()))
                .andExpect(jsonPath("$.data.comment").value(course.getComment()))
                .andExpect(jsonPath("$.data.level").value(course.getLevel()))
                .andExpect(jsonPath("$.data.race").value(course.getRace()))
                .andExpect(jsonPath("$.data.price").value(course.getPrice()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMemberInfo(role = "ROLE_ADMIN")
    @DisplayName("강의 리스트 조회 성공")
    void testSuccessGetCourseList() throws Exception {
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

        given(adminService.searchCourseList(any()))
                .willReturn(courseList);
        //when

        //then
        mockMvc.perform(get("/admin-management/courses"))
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

    @Test
    @WithMemberInfo(role = "ROLE_ADMIN")
    @DisplayName("강의 정보 수정 성공")
    void testUpdateCourseInfo() throws Exception{
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
                .id(course.getId())
                .gamer(gamer)
                .title("벙커링")
                .videoUrl("https://www.youtube.com/watch?v=2rpu0f-qog4")
                .thumbnailUrl("https://img.youtube.com/vi/2rpu0f-qog4/default.jpg")
                .comment("심화 8배럭 벙커링 강의")
                .level("고급")
                .race("테란")
                .price(20L)
                .build();

        UpdateCourse.Request request = UpdateCourse.Request.builder()
                .gamerId(gamer.getId())
                .title(updateCourse.getTitle())
                .videoUrl(updateCourse.getVideoUrl())
                .thumbnailUrl(updateCourse.getThumbnailUrl())
                .comment(updateCourse.getComment())
                .level(updateCourse.getLevel())
                .race(updateCourse.getRace())
                .price(updateCourse.getPrice())
                .build();

        given(adminService.updateCourseInfo(anyLong(), any()))
                .willReturn(CourseInfoOutputDto.Info.of(updateCourse));

        //when


        //then
        mockMvc.perform(put(String.format("/admin-management/course/%d", course.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(jsonPath("$.data.gamerName").value(updateCourse.getGamer().getName()))
                .andExpect(jsonPath("$.data.title").value(updateCourse.getTitle()))
                .andExpect(jsonPath("$.data.videoUrl").value(updateCourse.getVideoUrl()))
                .andExpect(jsonPath("$.data.thumbnailUrl").value(updateCourse.getThumbnailUrl()))
                .andExpect(jsonPath("$.data.comment").value(updateCourse.getComment()))
                .andExpect(jsonPath("$.data.level").value(updateCourse.getLevel()))
                .andExpect(jsonPath("$.data.race").value(updateCourse.getRace()))
                .andExpect(jsonPath("$.data.price").value(updateCourse.getPrice()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMemberInfo(role = "ROLE_ADMIN")
    @DisplayName("회원 리스트 조회 성공")
    void testSuccessGetMemberList() throws Exception {
        //given
        Member member = Member.builder()
                .email("email@kakao.com")
                .nickname("nick")
                .status(MemberStatus.PERMITTED)
                .authType(AuthType.KAKAO)
                .role(Role.ROLE_USER)
                .point(100)
                .build();

        List<MemberSearchOutputDto.Info> memberList = new ArrayList<>();
        memberList.add(MemberSearchOutputDto.Info.of(member));

        given(adminService.searchMemberList(any()))
                .willReturn(memberList);
        //when

        //then
        mockMvc.perform(get("/admin-management/members"))
                .andDo(print())
                .andExpect(jsonPath("$.data[0].email").value(member.getEmail()))
                .andExpect(jsonPath("$.data[0].nickname").value(member.getNickname()))
                .andExpect(jsonPath("$.data[0].status").value(member.getStatus().name()))
                .andExpect(jsonPath("$.data[0].authType").value(member.getAuthType().name()))
                .andExpect(jsonPath("$.data[0].role").value(member.getRole().name()))
                .andExpect(jsonPath("$.data[0].point").value(member.getPoint()))
                .andExpect(status().isOk());
    }


    @Test
    @WithMemberInfo(role = "ROLE_ADMIN")
    @DisplayName("회원 닉네임 수정 성공")
    void testUpdateMemberNickname() throws Exception{
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
                .id(1L)
                .email("email@kakao.com")
                .nickname("modify")
                .status(MemberStatus.PERMITTED)
                .authType(AuthType.KAKAO)
                .role(Role.ROLE_USER)
                .point(100)
                .build();

        given(adminService.memberNicknameChange(anyLong(), anyString()))
                .willReturn(MemberSearchOutputDto.Info.of(updateMember));

        //when


        //then
        mockMvc.perform(patch(String.format("/admin-management/members/nickname/%d", member.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateMember.getNickname())))
                .andDo(print())
                .andExpect(jsonPath("$.data.nickname").value(updateMember.getNickname()))
                .andExpect(status().isOk());
    }


}