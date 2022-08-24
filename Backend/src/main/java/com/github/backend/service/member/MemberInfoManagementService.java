package com.github.backend.service.member;

public interface MemberInfoManagementService {


	void editNickname(String memberEmail, String newNickname);

	void withdrawal(String memberEmail);


}
