package com.github.backend.web;

import com.github.backend.model.dto.RegisterCourse;
import com.github.backend.persist.entity.Course;
import com.github.backend.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/course")
public class CourseController {

    private final CourseService courseService;

    @PostMapping("/register")
    public RegisterCourse.Response registerCourse(
            @RequestBody @Valid RegisterCourse.Request request
    ){
        Course course = courseService.registerCourse(
                request.getGamerId(),
                request.getTitle(),
                request.getVideoUrl(),
                request.getThumbnailUrl(),
                request.getComment(),
                request.getLevel(),
                request.getRace(),
                request.getPrice()
        );

        return new RegisterCourse.Response(
                course.getGamer().getName(),
                course.getTitle(),
                course.getRace(),
                course.getLevel(),
                course.getComment(),
                course.getPrice()
        );
    }
}
