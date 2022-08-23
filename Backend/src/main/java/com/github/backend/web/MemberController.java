package com.github.backend.web;

import com.github.backend.model.Result;
import com.github.backend.model.dto.TokenMemberDto;
import com.github.backend.model.dto.WithdrawalMember;
import com.github.backend.persist.entity.Member;
import com.github.backend.persist.repository.RefreshTokenRepository;
import com.github.backend.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    final MemberService memberService;
    final RefreshTokenRepository refreshTokenRepository;

    @GetMapping("/withdrawal")
    public ResponseEntity<Result<?>> withdrawalMember(@AuthenticationPrincipal TokenMemberDto.MemberInfo memberInfo) {
        memberService.Withdrawal(memberInfo.getEmail());
        refreshTokenRepository.deleteByUsername(memberInfo.getEmail());
        Member afterMember = memberService.loadMemberInfo(memberInfo.getEmail());

        WithdrawalMember withdrawalMember = new WithdrawalMember(memberInfo.getEmail(), afterMember.getNickname(), afterMember.getStatus().toString());
        return ResponseEntity.ok().body(new Result<>(HttpStatus.OK.value(),true, withdrawalMember));
    }


}
