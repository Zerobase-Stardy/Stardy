package com.github.backend.persist.repository;

import java.time.Duration;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepository {
	private final RedisTemplate<String, String> redisTemplate;

	private static final String KEY_PREFIX = "refreshToken::";

	public void save(String username, String refreshToken, Duration duration) {
		ValueOperations<String, String> values = redisTemplate.opsForValue();
		values.set(KEY_PREFIX + username, refreshToken, duration);
	}
}
