package com.github.backend.persist.member.repository;

import com.github.backend.persist.member.Member;

import com.github.backend.persist.member.querydsl.condition.MemberSearchCondition;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import java.util.List;

import static com.github.backend.persist.gamer.QGamer.gamer;
import static com.github.backend.persist.member.QMember.member;
import static org.springframework.util.StringUtils.hasText;

@Repository
@RequiredArgsConstructor
public class MemberSearchRepository {
    private final JPAQueryFactory queryFactory;

    public Page<Member> searchByWhere(MemberSearchCondition condition, Pageable pageable){
        List<Member> result = queryFactory.selectFrom(member)
                .where(
                        emailContains(condition.getEmail()),
                        nicknameContains(condition.getNickname()),
                        pointGoe(condition.getPoint())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(result, pageable, getTotalPage(condition));
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

    private Long getTotalPage(MemberSearchCondition condition){
        Long count = queryFactory.select(gamer.count())
                .from(gamer)
                .where(
                        emailContains(condition.getEmail()),
                        nicknameContains(condition.getNickname()),
                        pointGoe(condition.getPoint())
                )
                .fetchOne();

        return count == null ? 0 : count;
    }
}
