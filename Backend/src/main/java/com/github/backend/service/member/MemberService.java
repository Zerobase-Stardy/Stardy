package com.github.backend.service.member;

import com.github.backend.persist.member.Member;

public interface MemberService {
    Member getMember(String email);
    Member getMember(Long memberId);
}
