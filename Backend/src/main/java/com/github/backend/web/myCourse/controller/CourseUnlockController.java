package com.github.backend.web.myCourse.controller;

import com.github.backend.dto.common.MemberInfo;
import com.github.backend.dto.common.Result;
import com.github.backend.service.myCourse.MyCourseUnlockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RequiredArgsConstructor
@RestController
public class CourseUnlockController {

	private final MyCourseUnlockService myCourseUnlockService;

	@PostMapping("/courses/{courseId}/unlock")
	public ResponseEntity<Result<?>> unlockCourse(@ApiIgnore  @AuthenticationPrincipal MemberInfo memberInfo, @PathVariable Long courseId) {

		myCourseUnlockService.unlockCourse(memberInfo.getId(),courseId);

		return ResponseEntity.ok()
			.body(Result.builder()
				.status(200)
				.success(true)
				.build());
	}

}
