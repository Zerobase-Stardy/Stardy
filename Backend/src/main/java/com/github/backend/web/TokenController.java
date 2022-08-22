package com.github.backend.web;

import com.github.backend.model.constants.MemberStatus;
import com.github.backend.model.dto.MemberLogout;
import com.github.backend.model.dto.TokenMemberDto;
import com.github.backend.model.dto.WithdrawalMember;
import com.github.backend.persist.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class TokenController {
   final RefreshTokenRepository refreshTokenRepository;

    @GetMapping("/logout")
    public MemberLogout.Response logout(HttpServletRequest request, HttpServletResponse response, @AuthenticationPrincipal TokenMemberDto.MemberInfo memberInfo){
        refreshTokenRepository.deleteByUsername(memberInfo.getEmail());

        String memberMail = memberInfo.getEmail();
        String memberNickName = memberInfo.getNickname();

        return new MemberLogout.Response(
                response.getStatus(),
                memberMail,
                memberNickName
        );
    }
}
