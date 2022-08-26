package com.github.backend.web.member.controller;

import static com.github.backend.dto.member.MemberWithdrawalDto.Request;
import com.github.backend.dto.common.MemberInfo;
import com.github.backend.dto.common.Result;
import com.github.backend.service.member.MemberInfoManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import springfox.documentation.annotations.ApiIgnore;


@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

	private final MemberInfoManagementService memberInfoManagementService;

	@GetMapping("/withdrawal")
	public ResponseEntity<Result<?>> withdrawal(@ApiIgnore  @AuthenticationPrincipal MemberInfo memberInfo) {
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
	public ResponseEntity<Result<?>> changeNickname(@ApiIgnore @AuthenticationPrincipal MemberInfo memberInfo,
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
