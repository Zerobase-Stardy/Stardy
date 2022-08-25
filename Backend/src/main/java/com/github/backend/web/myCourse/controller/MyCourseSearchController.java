package com.github.backend.web.myCourse.controller;

import com.github.backend.dto.common.MemberInfo;
import com.github.backend.dto.common.Result;
import com.github.backend.dto.myCourse.MyCourseSearchDto.Info;
import com.github.backend.dto.myCourse.MyCourseSearchDto.Request;
import com.github.backend.service.myCourse.MyCourseInfoSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MyCourseSearchController {

	private final MyCourseInfoSearchService myCourseInfoSearchService;

	@GetMapping("/members/me/courses")
	public ResponseEntity<Result<?>> searchMyCourse(@AuthenticationPrincipal MemberInfo memberInfo,Pageable pageable, Request request) {
		request.setMemberId(memberInfo.getId());
		Page<Info> infos = myCourseInfoSearchService.searchMyCourses(pageable, request);
		return ResponseEntity.ok().body(Result.builder()
			.status(200)
			.success(true)
			.data(infos)
			.build());
	}

}
