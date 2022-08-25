package com.github.backend.web.common;

import com.github.backend.dto.common.MemberInfo;
import com.github.backend.dto.common.Result;
import com.github.backend.dto.member.MemberLogout;
import com.github.backend.dto.common.TokenMemberDto;
import com.github.backend.persist.common.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class TokenController {
   final RefreshTokenRepository refreshTokenRepository;

    @GetMapping("/logout")
    public ResponseEntity<Result<?>> logout(@AuthenticationPrincipal MemberInfo memberInfo){
        refreshTokenRepository.deleteByUsername(memberInfo.getEmail());

        return ResponseEntity.ok().body(
                Result.builder()
                        .status(200)
                        .success(true)
                        .data(null)
                        .build()
        );
    }
}
