package com.github.backend.dto.gamer;

import com.github.backend.persist.course.repository.querydsl.condition.CourseSearchCondition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SearchCourse {
    private String title;
    private String level;
    private String race;


    public CourseSearchCondition toCondition() {
        return CourseSearchCondition.builder()
                .title(getTitle())
                .level(getLevel())
                .race(getRace())
                .build();
    }
}
