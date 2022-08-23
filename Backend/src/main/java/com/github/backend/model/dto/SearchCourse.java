package com.github.backend.model.dto;

import com.github.backend.persist.repository.querydsl.condition.CourseSearchCondition;
import com.github.backend.persist.repository.querydsl.condition.GamerSearchCondition;
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


    public CourseSearchCondition toCondition(){
        return CourseSearchCondition.builder()
                .title(getTitle())
                .level(getLevel())
                .race(getRace())
                .build();
    }
}
