package com.github.backend.service.impl;

import static com.github.backend.type.MemberErrorCode.MEMBER_NOT_EXISTS;

import com.github.backend.exception.MemberException;
import com.github.backend.persist.entity.Member;
import com.github.backend.persist.repository.MemberRepository;
import com.github.backend.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor

public class MemberServiceImpl implements MemberService {

    final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    @Override
    public Member getMember(String email) {
        return memberRepository.findByEmail(email)
            .orElseThrow(()-> new MemberException(MEMBER_NOT_EXISTS));
    }

    @Transactional(readOnly = true)
    @Override
    public Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
            .orElseThrow(()->new MemberException(MEMBER_NOT_EXISTS));
    }

}
