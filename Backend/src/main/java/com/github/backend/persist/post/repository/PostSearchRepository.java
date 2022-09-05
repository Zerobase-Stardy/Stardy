package com.github.backend.persist.post.repository;

import com.github.backend.persist.post.Post;
import com.github.backend.persist.post.QPost;
import com.github.backend.persist.post.repository.querydsl.TitleSearchCondition;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.github.backend.persist.post.QPost.*;
import static io.jsonwebtoken.lang.Strings.hasText;

@Repository
@RequiredArgsConstructor
public class PostSearchRepository {
    private final JPAQueryFactory queryFactory;

    public Page<Post> searchByWhere(TitleSearchCondition condition, Pageable pageable) {

        List<Post> result = queryFactory.select(post)
                .from(post)
                .where(
                        titleContain(condition.getTitle()),
                        boardKind(condition.getBoardKind())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
            .orderBy(post.createdDate.desc())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(post.count())
                .from(post)
                .where(
                        titleContain(condition.getTitle()),
                        boardKind(condition.getBoardKind())
                );


        return PageableExecutionUtils.getPage(result, pageable,
                countQuery::fetchOne);
    }

    private BooleanExpression titleContain(String title) {
        return hasText(title) ? post.title.contains(title) : null;
    }

    private BooleanExpression boardKind(String boardKind) {
        return hasText(boardKind) ? post.boardKind.contains(boardKind) : null;
    }
}
