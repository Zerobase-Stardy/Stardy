package com.github.backend.persist.course.repository.querydsl;

import static com.github.backend.persist.course.QCourse.*;
import static org.springframework.util.StringUtils.hasText;

import com.github.backend.persist.course.Course;


import com.github.backend.persist.course.repository.querydsl.condition.CourseSearchCondition;
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
public class CourseSearchRepository {
    private final JPAQueryFactory queryFactory;


    public Page<Course> searchByWhere(CourseSearchCondition condition, Pageable pageable){

        List<Course> result = queryFactory.select(course)
                .from(course)
                .where(
                        titleContain(condition.getTitle()),
                        levelEq(condition.getLevel()),
                        raceEq(condition.getRace())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        return new PageImpl<>(result, pageable, getTotalPage(condition));
    }


    private BooleanExpression titleContain(String title){
        return hasText(title) ? course.title.contains(title) : null;
    }

    private BooleanExpression levelEq(String level){
        return hasText(level) ? course.level.eq(level) : null;
    }

    private BooleanExpression raceEq(String race){
        return hasText(race) ? course.race.eq(race) : null;
    }

    private Long getTotalPage(CourseSearchCondition condition){
        Long count = queryFactory.select(course.count())
                .from(course)
                .where(
                        titleContain(condition.getTitle()),
                        levelEq(condition.getLevel()),
                        raceEq(condition.getRace())
                )
                .fetchOne();

        return count == null ? 0 : count;
    }
}
