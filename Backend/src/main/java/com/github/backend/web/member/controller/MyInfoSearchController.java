package com.github.backend.web.member.controller;

import com.github.backend.dto.common.MemberInfo;
import com.github.backend.dto.common.Result;
import com.github.backend.dto.member.SearchMember;
import com.github.backend.service.member.MemberSearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@Tag(name = "My Info - Search", description = "내 정보 조회 API")
@RequiredArgsConstructor
@RequestMapping("/members/me")
@RestController
public class MyInfoSearchController {

	private final MemberSearchService memberSearchService;

	@Operation(
		summary = "내 정보 조회", description = "내 정보를 조회합니다.",
		security = {@SecurityRequirement(name = "Authorization")},
		tags = {"My Info - Search"}
	)
	@GetMapping
	public ResponseEntity<Result<?>> searchMyInfo(@ApiIgnore @AuthenticationPrincipal MemberInfo memberInfo) {
		SearchMember searchMember = memberSearchService.searchMember(memberInfo.getId());

		return ResponseEntity.ok().body(
			Result.builder()
				.success(true)
				.status(200)
				.data(searchMember)
				.build());
	}

}
