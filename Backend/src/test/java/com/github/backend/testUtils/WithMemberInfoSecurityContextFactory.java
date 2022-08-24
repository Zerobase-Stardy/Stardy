package com.github.backend.testUtils;

import com.github.backend.dto.common.TokenMemberDto.MemberInfo;
import java.util.Collections;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMemberInfoSecurityContextFactory implements WithSecurityContextFactory<WithMemberInfo> {

	@Override
	public SecurityContext createSecurityContext(WithMemberInfo annotation) {

		MemberInfo memberInfo = MemberInfo.builder()
			.id(annotation.id())
			.email(annotation.email())
			.role(annotation.role())
			.nickname(annotation.nickname())
			.status(annotation.status())
			.build();

		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
			memberInfo, "",
			Collections.singletonList(new SimpleGrantedAuthority(annotation.role())));

		SecurityContext context = SecurityContextHolder.createEmptyContext();
		context.setAuthentication(token);

		return context;
	}
}
