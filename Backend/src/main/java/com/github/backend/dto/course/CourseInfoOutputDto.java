package com.github.backend.dto.course;

import com.github.backend.persist.course.Course;
import com.github.backend.persist.gamer.Gamer;
import lombok.Builder;
import lombok.Data;

public class CourseInfoOutputDto {

    @Builder
    @Data
    public static class Info {
        private Long id;
        private String title;
        private String videoUrl;
        private String thumbnailUrl;
        private String comment;
        private String level;
        private String race;
        private Long price;
        private String gamerName;

        public static CourseInfoOutputDto.Info of(Course course){
            return Info.builder()
                    .id(course.getId())
                    .title(course.getTitle())
                    .videoUrl(course.getVideoUrl())
                    .thumbnailUrl(course.getThumbnailUrl())
                    .comment(course.getComment())
                    .level(course.getLevel())
                    .comment(course.getComment())
                    .level(course.getLevel())
                    .race(course.getRace())
                    .price(course.getPrice())
                    .gamerName(course.getGamer().getName())
                    .build();
        }
    }
}
