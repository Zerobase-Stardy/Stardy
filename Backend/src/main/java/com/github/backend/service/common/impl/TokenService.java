package com.github.backend.service.common.impl;


import static javax.management.timer.Timer.ONE_MINUTE;

import com.github.backend.dto.common.LoginInfo;
import com.github.backend.dto.common.Tokens;
import com.github.backend.exception.common.JwtInvalidException;
import com.github.backend.exception.common.code.JwtErrorCode;
import com.github.backend.persist.common.repository.RefreshTokenRepository;
import com.github.backend.security.jwt.JwtInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.time.Duration;
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

	public Tokens issueAllToken(LoginInfo loginInfo) {
		return Tokens.builder()
			.accessToken(issueAccessToken(loginInfo))
			.refreshToken(issueRefreshToken(loginInfo))
			.build();
	}

	private String issueAccessToken(LoginInfo loginInfo) {
		return createToken(loginInfo, jwtInfo.getEncodedAccessKey(),
			jwtInfo.getAccessTokenExpiredMin());
	}

	private String issueRefreshToken(LoginInfo loginInfo) {
		String refreshToken = createToken(loginInfo, jwtInfo.getEncodedRefreshKey(),
			jwtInfo.getRefreshTokenExpiredMin());

		refreshTokenRepository.save(loginInfo.getEmail(), refreshToken, Duration.ofMinutes(
			jwtInfo.getRefreshTokenExpiredMin()));

		return refreshToken;
	}

	private String createToken(LoginInfo loginInfo, byte[] encodedSecretKey,int expiredMin) {
		Date now = new Date();

		Claims claims = loginInfo.toClaims();

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
