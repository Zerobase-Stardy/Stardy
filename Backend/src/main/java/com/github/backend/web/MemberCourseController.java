package com.github.backend.web;

import static com.github.backend.model.dto.MemberCourseDto.TakeRequest;

import com.github.backend.model.Result;
import com.github.backend.model.dto.TokenMemberDto.MemberInfo;
import com.github.backend.service.MemberCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/member")
@RestController
public class MemberCourseController {

	private final MemberCourseService memberCourseService;

	@PostMapping("/course")
	public ResponseEntity<Result<?>> takeCourse(
		@AuthenticationPrincipal MemberInfo memberInfo,
		@RequestBody TakeRequest request) {

		memberCourseService.takeCourse(memberInfo.getId(), request.getCourseId());

		return ResponseEntity.ok().body(new Result<>(200, true, ""));
	}



}
