package com.github.backend.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.backend.config.SecurityConfig;
import com.github.backend.dto.admin.CreateAdmin;
import com.github.backend.dto.course.RegisterCourse;
import com.github.backend.dto.course.UpdateCourse;
import com.github.backend.dto.gamer.RegisterGamer;
import com.github.backend.dto.gamer.UpdateGamer;
import com.github.backend.persist.admin.Admin;
import com.github.backend.persist.course.Course;
import com.github.backend.persist.gamer.Gamer;
import com.github.backend.persist.member.type.Role;
import com.github.backend.security.jwt.JwtAuthenticationProvider;
import com.github.backend.security.jwt.JwtEntryPoint;
import com.github.backend.security.oauth.OAuth2SuccessHandler;
import com.github.backend.service.admin.impl.AdminService;
import com.github.backend.service.common.impl.CustomOAuth2UserService;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(value = com.github.backend.web.AdminController.class,
        includeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)}
)
public class AdminControllerTest {

    @MockBean
    JwtAuthenticationProvider jwtAuthenticationProvider;

    @MockBean
    JwtEntryPoint jwtEntryPoint;

    @MockBean
    OAuth2SuccessHandler oAuth2SuccessHandler;

    @MockBean
    CustomOAuth2UserService customOAuth2UserService;

    @MockBean
    private AdminService adminService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Admin 생성 성공")
    void testRegisterAdmin() throws Exception {
        //given
        Admin admin = Admin.builder()
                .adminId("admin")
                .password("password")
                .role(Role.ROLE_ADMIN)
                .build();
        given(adminService.registerAdmin(anyString(), anyString()))
                .willReturn(admin);
        //when

        //then
        mockMvc.perform(post("/admin/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new CreateAdmin.Request(admin.getAdminId(), admin.getPassword())
                        ))
                ).andDo(print())
                .andExpect(jsonPath("$.data.adminId").value(admin.getAdminId()))
                .andExpect(jsonPath("$.data.password").value(admin.getPassword()))
                .andExpect(jsonPath("$.data.role").value(admin.getRole().name()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Gamer 등록 성공")
    void testSuccessGamerRegister() throws Exception{
        //given
        Gamer gamer = Gamer.builder()
                .name("유영진")
                .race("테란")
                .nickname("rush")
                .introduce("단단한 테란")
                .build();

        given(adminService.registerGamer(
                anyString(),
                anyString(),
                anyString(),
                anyString()))
                .willReturn(gamer);

        //when

        //then
        mockMvc.perform(post("/admin/gamer/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new RegisterGamer.Request(
                                        gamer.getName(),
                                        gamer.getRace(),
                                        gamer.getNickname(),
                                        gamer.getIntroduce()
                                )
                        ))
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value(gamer.getName()))
                .andExpect(jsonPath("$.data.race").value(gamer.getRace()))
                .andExpect(jsonPath("$.data.nickname").value(gamer.getNickname()))
                .andExpect(jsonPath("$.data.introduce").value(gamer.getIntroduce()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
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
                anyLong(),
                anyString(),
                anyString(),
                anyString(),
                anyString()
        )).willReturn(updateGamer);

        //when

        //then
        mockMvc.perform(put(String.format("/admin/gamer/%d", gamer.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                    new UpdateGamer.Request (
                                        gamer.getName(),
                                        gamer.getRace(),
                                        gamer.getNickname(),
                                        gamer.getIntroduce()
                                    )
                        )))
                .andDo(print())
                .andExpect(jsonPath("$.data.name").value(updateGamer.getName()))
                .andExpect(jsonPath("$.data.race").value(updateGamer.getRace()))
                .andExpect(jsonPath("$.data.nickname").value(updateGamer.getNickname()))
                .andExpect(jsonPath("$.data.introduce").value(updateGamer.getIntroduce()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
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
                .willReturn(gamer);
        //when

        //then
        mockMvc.perform(get(String.format("/admin/gamer/%d",gamer.getId())))
                .andDo(print())
                .andExpect(jsonPath("$.data.name").value(gamer.getName()))
                .andExpect(jsonPath("$.data.race").value(gamer.getRace()))
                .andExpect(jsonPath("$.data.nickname").value(gamer.getNickname()))
                .andExpect(jsonPath("$.data.introduce").value(gamer.getIntroduce()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    @DisplayName("게이머 리스트 조회 성공")
    void testSuccessGetGamerList() throws Exception {
        //given
        Gamer gamer = Gamer.builder()
                .name("유영진")
                .race("테란")
                .nickname("rush")
                .introduce("단단한 테란")
                .build();
        List<Gamer> gamerList = new ArrayList<>();
        gamerList.add(gamer);

        given(adminService.getGamerList(any()))
                .willReturn(gamerList);
        //when

        //then
        mockMvc.perform(get("/admin/gamer/list"))
                .andDo(print())
                .andExpect(jsonPath("$.data[0].name").value(gamer.getName()))
                .andExpect(jsonPath("$.data[0].race").value(gamer.getRace()))
                .andExpect(jsonPath("$.data[0].nickname").value(gamer.getNickname()))
                .andExpect(jsonPath("$.data[0].introduce").value(gamer.getIntroduce()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
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
        given(adminService.registerCourse(
                    anyLong(),
                    anyString(),
                    anyString(),
                    anyString(),
                    anyString(),
                    anyString(),
                    anyString(),
                    anyLong()
                ))
                .willReturn(course);
        //when

        //then
        mockMvc.perform(post("/admin/course/register")
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
                .andExpect(jsonPath("$.data.gamerName").value(course.getGamer().getName()))
                .andExpect(jsonPath("$.data.title").value(course.getTitle()))
                .andExpect(jsonPath("$.data.race").value(course.getRace()))
                .andExpect(jsonPath("$.data.level").value(course.getLevel()))
                .andExpect(jsonPath("$.data.comment").value(course.getComment()))
                .andExpect(jsonPath("$.data.price").value(course.getPrice()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
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
                .willReturn(course);
        //when

        //then
        mockMvc.perform(get(String.format("/admin/course/%d", course.getId())))
                .andDo(print())
                .andExpect(jsonPath("$.data.courseId").value(course.getId()))
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
    @WithMockUser
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

        List<Course> courseList = new ArrayList<>();
        courseList.add(course);

        given(adminService.searchCourseList(any()))
                .willReturn(courseList);
        //when

        //then
        mockMvc.perform(get("/admin/course/list"))
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
    @WithMockUser(roles = "ADMIN")
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

        given(adminService.updateCourseInfo(anyLong(), any()))
                .willReturn(updateCourse);

        //when


        //then
        mockMvc.perform(put(String.format("/admin/course/%d/edit", course.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new UpdateCourse.Request(
                                        updateCourse.getGamer().getId(),
                                        updateCourse.getTitle(),
                                        updateCourse.getVideoUrl(),
                                        updateCourse.getThumbnailUrl(),
                                        updateCourse.getComment(),
                                        updateCourse.getLevel(),
                                        updateCourse.getRace(),
                                        updateCourse.getPrice()
                                )
                        )))
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


}