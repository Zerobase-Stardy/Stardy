package com.github.backend.dto.member;

import com.github.backend.persist.member.Member;
import com.github.backend.persist.member.querydsl.condition.MemberSearchCondition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SearchMember {
    private String email;
    private String nickname;
    private Long point;

    public MemberSearchCondition toCondition(){
        return MemberSearchCondition.builder()
                .email(getEmail())
                .nickname(getNickname())
                .point(getPoint())
                .build();
    }

    public static SearchMember of(Member member) {
        return SearchMember.builder()
            .email(member.getEmail())
            .nickname(member.getNickname())
            .point(member.getPoint())
            .build();
    }

}
