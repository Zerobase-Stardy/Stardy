package com.github.backend.web.common;

import com.github.backend.dto.common.MemberInfo;
import com.github.backend.dto.common.Result;
import com.github.backend.persist.common.repository.RefreshTokenRepository;
import com.github.backend.security.jwt.JwtInfo;
import com.github.backend.security.jwt.TokenService;
import com.github.backend.security.jwt.Tokens;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
   private final RefreshTokenRepository refreshTokenRepository;
   private final TokenService tokenService;

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

    @PostMapping("/refresh")
    public ResponseEntity<Result<?>> reissueToken(@RequestBody String refreshToken){

        Tokens token = tokenService.refresh(refreshToken);

        return ResponseEntity.ok().body(
                Result.builder()
                        .status(200)
                        .success(true)
                        .data(token)
                        .build()
        );
    }


}
