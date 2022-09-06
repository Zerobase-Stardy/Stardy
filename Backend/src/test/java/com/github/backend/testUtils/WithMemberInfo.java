package com.github.backend.testUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.security.test.context.support.WithSecurityContext;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMemberInfoSecurityContextFactory.class)
public @interface WithMemberInfo {

	long id() default 1L;
	String email() default "test@test.com";
	String nickname() default "test";
	String status() default "PERMITTED";
	String role() default "ROLE_USER";



}
