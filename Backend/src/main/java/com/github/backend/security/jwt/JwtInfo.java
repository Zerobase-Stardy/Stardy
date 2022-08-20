package com.github.backend.security.jwt;

import java.util.Base64;
import javax.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class JwtInfo {

	public static final String BEARER_PREFIX = "Bearer ";
	public static final String KEY_ROLES = "roles";
	public static final String KEY_ID = "id";
	public static final String KEY_EMAIL = "email";

	@Value("${spring.jwt.access-secret}")
	private String accessKey;
	@Value("${spring.jwt.refresh-secret}")
	private String refreshKey;
	private byte[] encodedAccessKey;
	private byte[] encodedRefreshKey;

	@Value("${spring.jwt.access-expire-min}")
	private int accessTokenExpiredMin;
	@Value("${spring.jwt.refresh-expire-min}")
	private int refreshTokenExpiredMin;

	@PostConstruct
	protected void init() {
		encodedAccessKey = Base64.getEncoder().encodeToString(accessKey.getBytes()).getBytes();
		encodedRefreshKey = Base64.getEncoder().encodeToString(refreshKey.getBytes()).getBytes();
	}




}
