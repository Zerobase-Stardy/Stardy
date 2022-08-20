package com.github.backend.model.dto;

import com.github.backend.security.jwt.JwtInfo;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
public class TokenMemberDto {

	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	@Data
	public static class MemberInfo{
		private Long id;
		private String username;
		private String role;

		public static MemberInfo of(Claims claims) {
			return MemberInfo.builder()
				.id(claims.get(JwtInfo.KEY_ID, Long.class))
				.username(claims.get(JwtInfo.KEY_EMAIL, String.class))
				.role(claims.get(JwtInfo.KEY_ROLES,String.class))
				.build();
		}
	}

	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	@Data
	public static class Tokens{
		private String accessToken;
		private String refreshToken;
	}

}
