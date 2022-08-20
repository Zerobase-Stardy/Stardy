package com.github.backend.model.dto;

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
