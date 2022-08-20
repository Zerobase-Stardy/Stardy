package com.github.backend.service.impl;

import static com.github.backend.model.dto.TokenMemberDto.*;
import static com.github.backend.security.jwt.JwtInfo.KEY_EMAIL;
import static com.github.backend.security.jwt.JwtInfo.KEY_ID;
import static com.github.backend.security.jwt.JwtInfo.KEY_ROLES;
import static javax.management.timer.Timer.ONE_MINUTE;

import com.github.backend.persist.repository.RefreshTokenRepository;
import com.github.backend.security.jwt.JwtInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TokenServiceImpl {

	private final RefreshTokenRepository refreshTokenRepository;

	private final JwtInfo jwtInfo;

	public Tokens issueAllToken(MemberInfo memberInfo) {
		return Tokens.builder()
			.accessToken(issueAccessToken(memberInfo))
			.refreshToken(issueRefreshToken(memberInfo))
			.build();
	}

	private String issueAccessToken(MemberInfo memberInfo) {
		return createToken(memberInfo, jwtInfo.getEncodedAccessKey(),
			jwtInfo.getAccessTokenExpiredMin());
	}

	private String issueRefreshToken(MemberInfo memberInfo) {
		String refreshToken = createToken(memberInfo, jwtInfo.getEncodedRefreshKey(),
			jwtInfo.getRefreshTokenExpiredMin());

		refreshTokenRepository.save(memberInfo.getUsername(), refreshToken, Duration.ofMinutes(
			jwtInfo.getRefreshTokenExpiredMin()));

		return refreshToken;
	}

	private String createToken(MemberInfo memberInfo, byte[] encodedSecretKey,int expiredMin) {
		Date now = new Date();

		Claims claims = Jwts.claims();
		claims.put(KEY_ID, memberInfo.getId());
		claims.put(KEY_EMAIL, memberInfo.getUsername());
		claims.put(KEY_ROLES, Collections.singleton(memberInfo.getRole()));

		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(now)
			.setExpiration(new Date(now.getTime() + ONE_MINUTE * expiredMin))
			.signWith(Keys.hmacShaKeyFor(encodedSecretKey))
			.compact();
	}







}
