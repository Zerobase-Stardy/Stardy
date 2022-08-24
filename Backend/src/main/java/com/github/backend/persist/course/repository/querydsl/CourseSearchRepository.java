package com.github.backend.persist.course.repository.querydsl;

import static com.github.backend.persist.course.QCourse.course;
import static org.springframework.util.StringUtils.hasText;

import com.github.backend.persist.course.Course;


import com.github.backend.persist.course.QCourse;
import com.github.backend.persist.course.repository.querydsl.condition.CourseSearchCondition;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import static org.springframework.util.StringUtils.hasText;
import static com.github.backend.persist.course.QCourse.course;

@Repository
@RequiredArgsConstructor
public class CourseSearchRepository {
    private final JPAQueryFactory queryFactory;

    public List<Course> searchByWhere(CourseSearchCondition condition){

        return queryFactory.selectFrom(course)
                .where(
                        titleContain(condition.getTitle()),
                        levelEq(condition.getLevel()),
                        raceEq(condition.getRace())
                )
                .fetch();
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
}
