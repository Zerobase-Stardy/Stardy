package com.github.backend.web.myCourse.controller;

import com.github.backend.dto.common.MemberInfo;
import com.github.backend.dto.common.Result;
import com.github.backend.service.myCourse.MyCourseUnlockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@Tag(name = "myCourse",description = "내 강의 관련 API")
@RequiredArgsConstructor
@RestController
public class CourseUnlockController {

	private final MyCourseUnlockService myCourseUnlockService;

	@Operation(
		summary = "강의 해금", description = "강의를 해금합니다.",
		security = {@SecurityRequirement(name = "Authorization")},
		tags = {"myCourse"}
	)
	@PostMapping("/courses/{courseId}/unlock")
	public ResponseEntity<Result<?>> unlockCourse(@ApiIgnore @AuthenticationPrincipal MemberInfo memberInfo,
		@Parameter(description = "강의 Id") @PathVariable Long courseId) {

		myCourseUnlockService.unlockCourse(memberInfo.getId(),courseId);

		return ResponseEntity.ok()
			.body(Result.builder()
				.status(200)
				.success(true)
				.build());
	}

}
