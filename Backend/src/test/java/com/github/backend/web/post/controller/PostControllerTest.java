package com.github.backend.web.post.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.backend.config.SecurityConfig;
import com.github.backend.dto.Post.PostRegisterOutPutDto;
import com.github.backend.security.jwt.JwtAccessDeniedHandler;
import com.github.backend.security.jwt.JwtAuthenticationProvider;
import com.github.backend.security.jwt.JwtEntryPoint;
import com.github.backend.service.post.PostService;
import com.github.backend.service.post.impl.S3UploadService;
import com.github.backend.testUtils.WithMemberInfo;
import com.github.backend.web.course.controller.CourseUnlockController;
import com.github.backend.web.post.dto.PostReq;
import com.github.backend.web.post.dto.PostReq.Request;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(value = PostController.class
	, includeFilters = {
	@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)})
class PostControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@MockBean
	JwtAuthenticationProvider jwtAuthenticationProvider;
	@MockBean
	JwtEntryPoint jwtEntryPoint;
	@MockBean
	AuthenticationConfiguration authenticationConfiguration;
	@MockBean
	JwtAccessDeniedHandler jwtAccessDeniedHandler;

	@MockBean
	PostService postService;
	@MockBean
	S3UploadService s3UploadService;

	@WithMemberInfo
	@Test
	void registerPost() throws Exception {
		given(postService.registerPost(any(), any()))
			.willReturn(PostRegisterOutPutDto.Info.builder()
				.title("test")
				.build());

		Request request = Request.builder()
			.title("test")
			.boardKind("free")
			.content("test")
			.build();

		mockMvc.perform(post("/posts")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
			.andExpect(jsonPath("$.status").value(200))
			.andExpect(jsonPath("$.data.title").value("test"));


	}
}