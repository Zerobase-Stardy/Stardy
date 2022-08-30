package com.github.backend.persist.post.repository;

import com.github.backend.persist.post.Post;
import com.github.backend.persist.post.QPost;
import com.github.backend.persist.post.repository.querydsl.TitleSearchCondition;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostSearchRepository{
    private final JPAQueryFactory queryFactory;

    public List<Post> searchByWhere(TitleSearchCondition condition){

        return queryFactory.selectFrom(QPost.post)
                .fetch();
    }
}
