package com.github.backend.service;

import com.github.backend.exception.CourseException;
import com.github.backend.persist.entity.Course;
import com.github.backend.persist.entity.Gamer;
import com.github.backend.persist.repository.CourseRepository;
import com.github.backend.persist.repository.GamerRepository;
import com.github.backend.type.CourseErrorCode;
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

    private void validateRegisterCourse(String title) {
        if(courseRepository.existsByTitle(title))
            throw new CourseException(CourseErrorCode.EXIST_SAME_TITLE);
    }
}
