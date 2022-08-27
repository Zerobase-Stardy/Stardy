package com.github.backend.persist.member.repository;

import com.github.backend.persist.member.Member;

import com.github.backend.persist.member.querydsl.condition.MemberSearchCondition;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;

import static com.github.backend.persist.member.QMember.member;
import static org.springframework.util.StringUtils.hasText;

@Repository
@RequiredArgsConstructor
public class MemberSearchRepository {
    private final JPAQueryFactory queryFactory;

    public List<Member> searchByWhere(MemberSearchCondition condition){

        return queryFactory.selectFrom(member)
                .where(
                        emailContains(condition.getEmail()),
                        nicknameContains(condition.getNickname()),
                        pointGoe(condition.getPoint())
                )
                .fetch();
    }

    private BooleanExpression emailContains(String email){
        return hasText(email) ? member.email.contains(email) : null;
    }

    private BooleanExpression nicknameContains(String nickname){
        return hasText(nickname) ? member.nickname.contains(nickname) : null;
    }

    private BooleanExpression pointGoe(Long point){

        return point == null ? null : (point >=0 ? member.point.goe(point) : null);
    }
}
