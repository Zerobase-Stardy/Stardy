package com.github.backend.web.member.controller;

import static com.github.backend.dto.member.MemberWithdrawalDto.Request;
import static com.github.backend.dto.common.TokenMemberDto.MemberInfo;

import com.github.backend.dto.common.Result;
import com.github.backend.service.member.MemberInfoManagementService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

	private final MemberInfoManagementService memberInfoManagementService;

	@GetMapping("/withdrawal")
	public ResponseEntity<Result<?>> withdrawal(@AuthenticationPrincipal MemberInfo memberInfo) {
		memberInfoManagementService.withdrawal(memberInfo.getEmail());
		return ResponseEntity.ok().body(new Result<>(HttpStatus.OK.value(), true, null));
	}

	@PatchMapping("/nickname")
	public ResponseEntity<Result<?>> changeNickname(@AuthenticationPrincipal MemberInfo memberInfo,
		@RequestBody @Valid Request request) {
		memberInfoManagementService.editNickname(memberInfo.getEmail(), request.getNickname());
		return ResponseEntity.ok().body(new Result<>(HttpStatus.OK.value(), true, null));
	}


}
