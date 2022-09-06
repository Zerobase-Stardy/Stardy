package com.github.backend.service.member.impl;

import com.github.backend.exception.member.MemberException;
import com.github.backend.exception.member.code.MemberErrorCode;
import com.github.backend.persist.member.repository.MemberRepository;
import com.github.backend.persist.member.type.MemberStatus;
import com.github.backend.persist.member.Member;
import com.github.backend.persist.common.repository.RefreshTokenRepository;
import com.github.backend.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

	private final RefreshTokenRepository refreshTokenRepository;
	private final MemberRepository memberRepository;

	@Transactional
	@Override
	public void editNickname(String memberEmail, String newNickname) {
		Member member = memberRepository.findByEmail(memberEmail)
			.orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_EXISTS));
		member.changeNickname(newNickname);
	}

	@Transactional
	@Override
	public void withdrawal(String memberEmail) {
		Member member = memberRepository.findByEmail(memberEmail)
			.orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_EXISTS));

		member.changeStatus(MemberStatus.WITHDRAWAL);
		refreshTokenRepository.deleteByUsername(memberEmail);
	}

}
