package com.github.backend.service;

import com.github.backend.persist.entity.Member;

public interface MemberService {
    Member getMember(String email);
    Member getMember(Long memberId);
}
