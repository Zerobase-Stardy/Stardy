package com.github.backend.service.member;

public interface MemberService {
	void editNickname(String memberEmail, String newNickname);
	void withdrawal(String memberEmail);


}
