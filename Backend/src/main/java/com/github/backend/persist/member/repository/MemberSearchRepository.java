package com.github.backend.persist.member.repository;

import com.github.backend.persist.course.Course;
import com.github.backend.persist.member.Member;

import com.github.backend.persist.member.querydsl.condition.MemberSearchCondition;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import java.util.List;

import static com.github.backend.persist.course.QCourse.course;
import static com.github.backend.persist.member.QMember.member;
import static org.springframework.util.StringUtils.hasText;

@Repository
@RequiredArgsConstructor
public class MemberSearchRepository {
    private final JPAQueryFactory queryFactory;

    public Page<Member> searchByWhere(MemberSearchCondition condition, Pageable pageable){
        QueryResults<Member> result = queryFactory.selectFrom(member)
                .where(
                        emailContains(condition.getEmail()),
                        nicknameContains(condition.getNickname()),
                        pointGoe(condition.getPoint())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
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
