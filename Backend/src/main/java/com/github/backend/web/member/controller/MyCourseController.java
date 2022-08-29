package com.github.backend.web.member.controller;

import com.github.backend.dto.common.MemberInfo;
import com.github.backend.dto.common.Result;
import com.github.backend.dto.myCourse.BookMarkToggleDto;
import com.github.backend.dto.myCourse.MyCourseSearchDto.Info;
import com.github.backend.dto.myCourse.MyCourseSearchDto.Request;
import com.github.backend.service.myCourse.MyCourseBookmarkService;
import com.github.backend.service.myCourse.MyCourseInfoSearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@Tag(name = "My Course", description = "내 강의에 관련된 API들 입니다.")
@RequiredArgsConstructor
@RequestMapping("/members/me/courses")
@RestController
public class MyCourseController {

	private final MyCourseInfoSearchService myCourseInfoSearchService;

	private final MyCourseBookmarkService myCourseBookmarkService;


	@Operation(
		summary = "내 강의 조회", description = "해금한 강의를 조회합니다.",
		security = {@SecurityRequirement(name = "Authorization")},
		tags = {"My Course"}
	)
	@GetMapping
	public ResponseEntity<Result<?>> searchMyCourse(
		@ApiIgnore @AuthenticationPrincipal MemberInfo memberInfo,
		@PageableDefault(size = 10, page = 0) Pageable pageable, Request request) {

		request.setMemberId(memberInfo.getId());
		Page<Info> infos = myCourseInfoSearchService.searchMyCourses(pageable, request);

		return ResponseEntity.ok().body(Result.builder()
			.status(200)
			.success(true)
			.data(infos)
			.build());
	}


	@Operation(
		summary = "내 강의 즐겨찾기", description = "해금한 강의를 즐겨찾기하거나 해제합니다",
		security = {@SecurityRequirement(name = "Authorization")},
		tags = {"My Course"}
	)
	@PostMapping("/{courseId}/bookmark")
	public ResponseEntity<Result<?>> toggleBookmark(
		@ApiIgnore @AuthenticationPrincipal MemberInfo memberInfo,
		@Parameter(description = "강의 Id")@PathVariable Long courseId) {

		boolean bookmark
			= myCourseBookmarkService.toggleBookmark(memberInfo.getId(), courseId);

		return ResponseEntity.ok().body(Result.builder()
			.success(true)
			.status(200)
			.data(BookMarkToggleDto.Info.builder()
				.bookmark(bookmark)
				.build())
			.build());
	}




}
