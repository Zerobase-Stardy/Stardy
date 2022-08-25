package com.github.backend.dto.common;

import static com.github.backend.security.jwt.JwtInfo.KEY_EMAIL;
import static com.github.backend.security.jwt.JwtInfo.KEY_ID;
import static com.github.backend.security.jwt.JwtInfo.KEY_ROLES;

import com.github.backend.security.jwt.JwtInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AdminInfo implements LoginInfo{

	private Long id;
	private String email;
	private String role;

	@Override
	public Claims toClaims() {
		Claims claims = Jwts.claims();

		claims.put(KEY_ID, this.id);
		claims.put(KEY_EMAIL, this.email);
		claims.put(KEY_ROLES, this.role);

		return claims;
	}

	public static AdminInfo of(Claims claims) {
		return AdminInfo.builder()
			.id(claims.get(JwtInfo.KEY_ID, Long.class))
			.email(claims.get(JwtInfo.KEY_EMAIL, String.class))
			.role(claims.get(JwtInfo.KEY_ROLES,String.class))
			.build();
	}
}
