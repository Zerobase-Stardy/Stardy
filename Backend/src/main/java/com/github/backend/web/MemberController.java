package com.github.backend.web;


import com.github.backend.model.constants.MemberStatus;
import com.github.backend.model.dto.MemberInput;
import com.github.backend.model.dto.ModifyMember;
import com.github.backend.model.dto.TokenMemberDto;
import com.github.backend.model.dto.WithdrawalMember;
import com.github.backend.persist.entity.Member;
import com.github.backend.persist.repository.RefreshTokenRepository;
import com.github.backend.service.MemberService;
import com.nimbusds.openid.connect.sdk.claims.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    final MemberService memberService;
    final RefreshTokenRepository refreshTokenRepository;

    @GetMapping("/withdrawal")
    public WithdrawalMember.Response withdrawalMember(@AuthenticationPrincipal TokenMemberDto.MemberInfo memberInfo, HttpServletResponse response) {
        TokenMemberDto.MemberInfo memberInfo1 = memberInfo;

        memberService.Withdrawal(memberInfo.getEmail());
        refreshTokenRepository.deleteByUsername(memberInfo.getEmail());

        return new WithdrawalMember.Response(
                response.getStatus(),
                memberInfo.getEmail(),
                memberInfo.getNickname(),
                memberService.loadMemberInfo(memberInfo.getEmail()).getStatus().toString()
        );
    }

    @PutMapping("/nickname")
    public ModifyMember.Response modifyMember(@AuthenticationPrincipal TokenMemberDto.MemberInfo memberInfo, MemberInput memberInput ,HttpServletResponse response) {

        System.out.println(memberInfo);
        System.out.println(memberInput.getNickName());
        memberService.modifyNickNameMember(memberInfo.getEmail(), memberInput.getNickName());

        return new ModifyMember.Response(
                response.getStatus(),
                memberInfo.getEmail(),
                memberService.loadMemberInfo(memberInfo.getEmail()).getNickname()
        );
    }


}
