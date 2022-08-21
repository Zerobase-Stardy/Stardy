package com.github.backend.service;

import com.github.backend.exception.CourseException;
import com.github.backend.persist.entity.Course;
import com.github.backend.persist.repository.CourseRepository;
import com.github.backend.type.CourseErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;

    @Transactional
    public Course getCourseInfo(Long courseId){

        return courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseException(CourseErrorCode.NOT_EXIST_COURSE));
    }

}
