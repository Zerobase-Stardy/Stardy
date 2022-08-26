package com.github.backend.persist.common.type;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AuthType {
	KAKAO("카카오톡"),
	GOOGLE("구글");

	private String korName;

}
