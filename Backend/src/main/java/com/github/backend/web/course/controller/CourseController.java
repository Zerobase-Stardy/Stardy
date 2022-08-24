package com.github.backend.web.course.controller;

import com.github.backend.dto.course.CourseInfoResponse;
import com.github.backend.service.course.impl.CourseService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/course")
public class CourseController {

    private final CourseService courseService;

    @GetMapping("/{courseId}")
    public CourseInfoResponse getCourseInfo(
        @PathVariable("courseId") @Valid Long courseId
    ) {
        return CourseInfoResponse.from(
            courseService.getCourseInfo(courseId)
        );
    }


}
