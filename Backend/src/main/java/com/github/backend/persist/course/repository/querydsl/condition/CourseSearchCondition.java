package com.github.backend.persist.course.repository.querydsl.condition;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CourseSearchCondition {
    String title;
    String level;
    String race;

    public CourseSearchCondition(String title, String level, String race){
        this.title = title;
        this.level = level;
        this.race = race;
    }

}
