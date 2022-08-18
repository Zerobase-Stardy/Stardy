package com.github.backend.service;

import com.github.backend.exception.CourseException;
import com.github.backend.exception.GamerException;
import com.github.backend.model.dto.UpdateCourse;
import com.github.backend.persist.entity.Course;
import com.github.backend.persist.entity.Gamer;
import com.github.backend.persist.repository.CourseRepository;
import com.github.backend.persist.repository.GamerRepository;
import com.github.backend.type.CourseErrorCode;
import com.github.backend.type.GamerErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final GamerRepository gamerRepository;

    @Transactional
    public Course registerCourse(
            Long gamerId,
            String title,
            String videoUrl,
            String thumbnailUrl,
            String comment,
            String level,
            String race,
            Long price
    ){
       Gamer gamer = gamerRepository.findById(gamerId)
               .orElseThrow(() -> new CourseException(CourseErrorCode.NOT_EXIST_GAMER));

       validateRegisterCourse(title);

       return courseRepository.save(
               Course.builder()
                       .title(title)
                       .videoUrl(videoUrl)
                       .thumbnailUrl(thumbnailUrl)
                       .comment(comment)
                       .level(level)
                       .race(race)
                       .price(price)
                       .gamer(gamer)
                       .build()
       );
    }

    @Transactional
    public Course getCourseInfo(Long courseId){

        return courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseException(CourseErrorCode.NOT_EXIST_COURSE));
    }

    @Transactional
    public Course updateCourseInfo(Long courseId, UpdateCourse.Request request){

        Gamer gamer = gamerRepository.findById(request.getGamerId())
                .orElseThrow(() -> new CourseException(CourseErrorCode.NOT_EXIST_GAMER));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseException(CourseErrorCode.NOT_EXIST_COURSE));

        course.setTitle(request.getTitle());
        course.setVideoUrl(request.getVideoUrl());
        course.setThumbnailUrl(request.getThumbnailUrl());
        course.setComment(request.getComment());
        course.setLevel(request.getLevel());
        course.setRace(request.getRace());
        course.setPrice(request.getPrice());
        course.setGamer(gamer);

        return courseRepository.save(course);
    }

    private void validateRegisterCourse(String title) {
        if(courseRepository.existsByTitle(title))
            throw new CourseException(CourseErrorCode.EXIST_SAME_TITLE);
    }


}
