package com.github.backend.service.impl;

import com.github.backend.exception.MemberException;
import com.github.backend.model.constants.MemberStatus;
import com.github.backend.persist.entity.Member;
import com.github.backend.persist.repository.MemberRepository;
import com.github.backend.service.MemberService;
import com.github.backend.type.MemberErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor

public class MemberServiceImp implements MemberService {

    final MemberRepository memberRepository;

    @Override
    public Member loadMemberInfo(String email) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);

        if (!optionalMember.isPresent()){
            new MemberException(MemberErrorCode.MEMBER_NOT_EXISTS);
        }

        Member member = optionalMember.get();

        return member;
    }

    @Override
    @Transactional
    public void Withdrawal(String email ) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);

        Member member = optionalMember.get();

        memberRepository.save(
                Member.builder()
                        .email(member.getEmail())
                        .id(member.getId())
                        .nickname(member.getNickname())
                        .point(member.getPoint())
                        .authType(member.getAuthType())
                        .role(member.getRole())
                        .status(MemberStatus.WITHDRAWAL)
                        .build()
        );
    }

}
