package com.github.backend.service.impl;

import com.github.backend.model.constants.MemberStatus;
import com.github.backend.persist.entity.Member;
import com.github.backend.persist.repository.RefreshTokenRepository;
import com.github.backend.service.MemberInfoManagementService;
import com.github.backend.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberInfoManagementServiceImpl implements MemberInfoManagementService {

	private final RefreshTokenRepository refreshTokenRepository;
	private final MemberService memberService;

	@Transactional
	@Override
	public void editNickname(String memberEmail, String newNickname) {
		Member member = memberService.getMember(memberEmail);
		member.changeNickname(newNickname);
	}

	@Transactional
	@Override
	public void withdrawal(String memberEmail) {
		Member member = memberService.getMember(memberEmail);
		member.changeStatus(MemberStatus.WITHDRAWAL);
		refreshTokenRepository.deleteByUsername(memberEmail);
	}

}
