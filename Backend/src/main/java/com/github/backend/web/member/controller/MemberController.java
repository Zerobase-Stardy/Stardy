package com.github.backend.web.member.controller;

import com.github.backend.dto.common.Result;
import com.github.backend.service.member.MemberInfoManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.github.backend.dto.common.TokenMemberDto.MemberInfo;
import static com.github.backend.dto.member.MemberWithdrawalDto.Request;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

	private final MemberInfoManagementService memberInfoManagementService;

	@GetMapping("/withdrawal")
	public ResponseEntity<Result<?>> withdrawal(@AuthenticationPrincipal MemberInfo memberInfo) {
		memberInfoManagementService.withdrawal(memberInfo.getEmail());
		return ResponseEntity.ok().body(
				Result.builder()
						.status(200)
						.success(true)
						.data(null)
						.build()
		);
	}


	@PatchMapping("/nickname")
	public ResponseEntity<Result<?>> changeNickname(@AuthenticationPrincipal MemberInfo memberInfo,
		@RequestBody @Valid Request request) {
		memberInfoManagementService.editNickname(memberInfo.getEmail(), request.getNickname());
		return ResponseEntity.ok().body(
				Result.builder()
						.status(200)
						.success(true)
						.data(null)
						.build()
		);
	}


}
