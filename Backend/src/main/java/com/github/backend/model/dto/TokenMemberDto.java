package com.github.backend.model.dto;

import com.github.backend.model.constants.MemberStatus;
import com.github.backend.security.jwt.JwtInfo;
import io.jsonwebtoken.Claims;
import java.util.Map;
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
		private String email;
		private String nickname;
		private String status;
		private String role;

		public static MemberInfo of(Claims claims) {

			return MemberInfo.builder()
				.id(claims.get(JwtInfo.KEY_ID, Long.class))
				.email(claims.get(JwtInfo.KEY_EMAIL, String.class))
				.role(claims.get(JwtInfo.KEY_ROLES,String.class))
				.status(claims.get(JwtInfo.KEY_STATUS,String.class))
				.nickname(claims.get(JwtInfo.KEY_NICKNAME,String.class))
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
