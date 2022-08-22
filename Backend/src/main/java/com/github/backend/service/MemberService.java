package com.github.backend.service;

import com.github.backend.model.dto.TokenMemberDto;
import com.github.backend.persist.entity.Member;

public interface MemberService {
    void Withdrawal(String email);

    void modifyNickNameMember(String email, String nickName);

    Member loadMemberInfo(String email);
}
