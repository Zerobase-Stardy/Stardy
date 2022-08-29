package com.github.backend.service.member.impl;

import com.github.backend.dto.member.SearchMember;
import com.github.backend.exception.member.MemberException;
import com.github.backend.exception.member.code.MemberErrorCode;
import com.github.backend.persist.member.repository.MemberRepository;
import com.github.backend.service.member.MemberSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberSearchServiceImpl implements MemberSearchService {

	private final MemberRepository memberRepository;

	@Transactional(readOnly = true)
	@Override
	public SearchMember searchMember(Long memberId) {
		return SearchMember.of(memberRepository.findById(memberId)
			.orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_EXISTS)));
	}


}
