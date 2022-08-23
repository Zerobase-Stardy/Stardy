package com.github.backend.service;

import com.github.backend.persist.entity.Member;

public interface MemberService {
    void Withdrawal(String email);

    Member loadMemberInfo(String email);
}
