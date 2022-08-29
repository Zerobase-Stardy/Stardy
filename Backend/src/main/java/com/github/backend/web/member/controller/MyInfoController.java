package com.github.backend.web.member.controller;

import static com.github.backend.dto.member.MemberWithdrawalDto.Request;

import com.github.backend.dto.common.MemberInfo;
import com.github.backend.dto.common.Result;
import com.github.backend.service.member.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;


@Tag(name = "My Info - Control", description = "내 정보를 변경할 때 사용합니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/members/me")
public class MyInfoController {

	private final MemberService memberService;

	@Operation(
		summary = "회원 탈퇴", description = "회원 탈퇴합니다.",
		security = {@SecurityRequirement(name = "Authorization")},
		tags = {"My Info - Control"}
	)
	@GetMapping("/withdrawal")
	public ResponseEntity<Result<?>> withdrawal(@ApiIgnore  @AuthenticationPrincipal MemberInfo memberInfo) {
		memberService.withdrawal(memberInfo.getEmail());
		return ResponseEntity.ok().body(
				Result.builder()
						.status(200)
						.success(true)
						.data(null)
						.build()
		);
	}

	@Operation(
		summary = "내 닉네임 변경", description = "내 닉네임을 변경합니다.",
		security = {@SecurityRequirement(name = "Authorization")},
		tags = {"My Info - Control"}
	)
	@PatchMapping("/nickname")
	public ResponseEntity<Result<?>> changeNickname(@ApiIgnore @AuthenticationPrincipal MemberInfo memberInfo,
		@RequestBody @Valid Request request) {
		memberService.editNickname(memberInfo.getEmail(), request.getNickname());
		return ResponseEntity.ok().body(
				Result.builder()
						.status(200)
						.success(true)
						.data(null)
						.build()
		);
	}


}
