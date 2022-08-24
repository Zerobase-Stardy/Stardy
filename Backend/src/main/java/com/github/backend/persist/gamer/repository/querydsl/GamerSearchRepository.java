package com.github.backend.persist.gamer.repository.querydsl;

import static com.github.backend.persist.entity.QGamer.gamer;
import static org.springframework.util.StringUtils.hasText;

import com.github.backend.persist.gamer.Gamer;
import com.github.backend.persist.gamer.repository.querydsl.condition.GamerSearchCondition;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
