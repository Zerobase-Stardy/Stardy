package com.github.backend.service.member;


import com.github.backend.dto.member.SearchMember;

public interface MemberSearchService {

	SearchMember searchMember(Long memberId);

}
