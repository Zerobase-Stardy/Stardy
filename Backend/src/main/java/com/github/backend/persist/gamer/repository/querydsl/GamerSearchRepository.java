package com.github.backend.persist.gamer.repository.querydsl;


import static com.github.backend.persist.gamer.QGamer.gamer;
import static org.springframework.util.StringUtils.hasText;

import com.github.backend.persist.gamer.Gamer;
import com.github.backend.persist.gamer.repository.querydsl.condition.GamerSearchCondition;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GamerSearchRepository {
    private final JPAQueryFactory queryFactory;

    public Page<Gamer> searchByWhere(GamerSearchCondition condition, Pageable pageable){

        List<Gamer> result = queryFactory.select(gamer)
                .from(gamer)
                .where(
                        gamerNameEq(condition.getName()),
                        raceEq(condition.getRace()),
                        gamerNickname(condition.getNickname())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        return new PageImpl<>(result, pageable, getTotalPage(condition));
    }

    private BooleanExpression gamerNameEq(String name){
        return hasText(name) ? gamer.name.eq(name) : null;
    }

    private BooleanExpression raceEq(String race){
        return hasText(race) ? gamer.race.eq(race) : null;
    }

    private BooleanExpression gamerNickname(String nickname){
        return hasText(nickname) ? gamer.nickname.eq(nickname) : null;
    }

    private Long getTotalPage(GamerSearchCondition condition){
        Long count = queryFactory.select(gamer.count())
                .from(gamer)
                .where(
                        gamerNameEq(condition.getName()),
                        raceEq(condition.getRace()),
                        gamerNickname(condition.getNickname())
                )
                .fetchOne();

        return count == null ? 0 : count;
    }

}
