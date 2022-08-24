package com.github.backend.dto.course;

import com.github.backend.persist.course.Course;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseInfoResponse {

    private Long courseId;
    private String gamerName;
    private String title;
    private String videoUrl;
    private String thumbnailUrl;
    private String comment;
    private String level;
    private String race;
    private Long price;


    public static CourseInfoResponse from(Course course){


        return CourseInfoResponse.builder()
                .courseId(course.getId())
                .gamerName(course.getGamer().getName())
                .title(course.getTitle())
                .videoUrl(course.getVideoUrl())
                .thumbnailUrl(course.getThumbnailUrl())
                .comment(course.getComment())
                .level(course.getLevel())
                .race(course.getRace())
                .price(course.getPrice())
                .build();
    }
}
