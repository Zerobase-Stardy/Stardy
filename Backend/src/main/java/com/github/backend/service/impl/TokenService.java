package com.github.backend.service.impl;

import static com.github.backend.model.dto.TokenMemberDto.*;
import static com.github.backend.security.jwt.JwtInfo.KEY_EMAIL;
import static com.github.backend.security.jwt.JwtInfo.KEY_ID;
import static com.github.backend.security.jwt.JwtInfo.KEY_NICKNAME;
import static com.github.backend.security.jwt.JwtInfo.KEY_ROLES;
import static com.github.backend.security.jwt.JwtInfo.KEY_STATUS;
import static javax.management.timer.Timer.ONE_MINUTE;

import com.github.backend.exception.JwtInvalidException;
import com.github.backend.persist.repository.RefreshTokenRepository;
import com.github.backend.security.jwt.JwtInfo;
import com.github.backend.type.JwtErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class TokenService {

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

		refreshTokenRepository.save(memberInfo.getEmail(), refreshToken, Duration.ofMinutes(
			jwtInfo.getRefreshTokenExpiredMin()));

		return refreshToken;
	}

	private String createToken(MemberInfo memberInfo, byte[] encodedSecretKey,int expiredMin) {
		Date now = new Date();

		Claims claims = Jwts.claims();
		claims.put(KEY_ID, memberInfo.getId());
		claims.put(KEY_EMAIL, memberInfo.getEmail());
		claims.put(KEY_NICKNAME, memberInfo.getNickname());
		claims.put(KEY_STATUS, memberInfo.getStatus());
		claims.put(KEY_ROLES, Collections.singleton(memberInfo.getRole()));

		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(now)
			.setExpiration(new Date(now.getTime() + ONE_MINUTE * expiredMin))
			.signWith(Keys.hmacShaKeyFor(encodedSecretKey))
			.compact();
	}

	public boolean existsRefreshToken(String username) {
		return refreshTokenRepository.existsByUsername(username);
	}
	public void deleteRefreshToken(String username) {
		refreshTokenRepository.deleteByUsername(username);
	}



	public Claims parseAccessToken(String token) {
		return parseToken(token, jwtInfo.getEncodedAccessKey());
	}
	public Claims parseRefreshToken(String token) {
		return parseToken(token, jwtInfo.getEncodedRefreshKey());
	}

	private Claims parseToken(String token,byte[] encodedKey) {
		Claims claims;
		try {
			claims = Jwts.parserBuilder()
				.setSigningKey(encodedKey)
				.build()
				.parseClaimsJws(token).getBody();
		} catch (SignatureException e) {
			log.warn("Invalid JWT signature: {}", e.getMessage());
			throw new JwtInvalidException(JwtErrorCode.INVALID_JWT_SIGNATURE, e);
		} catch (MalformedJwtException e) {
			log.warn("Invalid JWT token: {}", e.getMessage());
			throw new JwtInvalidException(JwtErrorCode.INVALID_JWT_SIGNATURE, e);
		} catch (ExpiredJwtException e) {
			log.warn("JWT token is expired: {}", e.getMessage());
			throw new JwtInvalidException(JwtErrorCode.EXPIRED_JWT, e);
		} catch (UnsupportedJwtException e) {
			log.warn("JWT token is unsupported: {}", e.getMessage());
			throw new JwtInvalidException(JwtErrorCode.INVALID_JWT_SIGNATURE, e);
		} catch (IllegalArgumentException e) {
			log.warn("JWT claims string is empty or null: {}", e.getMessage());
			throw new JwtInvalidException(JwtErrorCode.INVALID_JWT_SIGNATURE, e);
		}
		return claims;
	}


}
