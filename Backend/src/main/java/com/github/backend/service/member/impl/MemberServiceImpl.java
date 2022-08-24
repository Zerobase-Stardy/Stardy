package com.github.backend.service.member.impl;

import com.github.backend.exception.member.MemberException;
import com.github.backend.persist.member.Member;
import com.github.backend.persist.member.repository.MemberRepository;
import com.github.backend.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.github.backend.exception.member.code.MemberErrorCode.MEMBER_NOT_EXISTS;

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
