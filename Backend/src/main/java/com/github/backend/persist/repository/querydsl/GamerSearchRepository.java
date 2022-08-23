package com.github.backend.persist.repository.querydsl;

import com.github.backend.persist.entity.Gamer;
import com.github.backend.persist.repository.querydsl.condition.GamerSearchCondition;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.github.backend.persist.entity.QGamer.gamer;
import static org.springframework.util.StringUtils.hasText;

@Repository
@RequiredArgsConstructor
public class GamerSearchRepository {
    private final JPAQueryFactory queryFactory;

    public List<Gamer> searchByWhere(GamerSearchCondition condition){

        return queryFactory.selectFrom(gamer)
                .where(
                        gamerNameEq(condition.getName()),
                        raceEq(condition.getRace()),
                        gamerNickname(condition.getNickname())
                )
                .fetch();
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

}
