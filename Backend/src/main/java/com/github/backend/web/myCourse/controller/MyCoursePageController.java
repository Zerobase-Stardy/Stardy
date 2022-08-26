package com.github.backend.web.myCourse.controller;

import com.github.backend.dto.common.MemberInfo;
import com.github.backend.dto.common.Result;
import com.github.backend.dto.myCourse.BookMarkToggleDto;
import com.github.backend.dto.myCourse.MyCourseSearchDto.Info;
import com.github.backend.dto.myCourse.MyCourseSearchDto.Request;
import com.github.backend.service.myCourse.MyCourseBookmarkService;
import com.github.backend.service.myCourse.MyCourseInfoSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RequiredArgsConstructor
@RequestMapping("/members/me/courses")
@RestController
public class MyCoursePageController {

	private final MyCourseInfoSearchService myCourseInfoSearchService;
	private final MyCourseBookmarkService myCourseBookmarkService;

	@GetMapping
	public ResponseEntity<Result<?>> searchMyCourse(@ApiIgnore  @AuthenticationPrincipal MemberInfo memberInfo,
		Pageable pageable, Request request) {
		request.setMemberId(memberInfo.getId());
		Page<Info> infos = myCourseInfoSearchService.searchMyCourses(pageable, request);
		return ResponseEntity.ok().body(Result.builder()
			.status(200)
			.success(true)
			.data(infos)
			.build());
	}

	@PostMapping("/{courseId}/bookmark")
	public ResponseEntity<Result<?>> toggleBookmark(@ApiIgnore @AuthenticationPrincipal MemberInfo memberInfo,
		@PathVariable Long courseId) {

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
