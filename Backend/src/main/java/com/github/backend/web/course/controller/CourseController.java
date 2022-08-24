package com.github.backend.web;

import com.github.backend.dto.course.CourseInfoResponse;
import com.github.backend.service.course.impl.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/course")
public class CourseController {

    private final CourseService courseService;

    @GetMapping("/{courseId}")
    public CourseInfoResponse getCourseInfo(
            @PathVariable("courseId") @Valid Long courseId
    ){
        return CourseInfoResponse.from(
                courseService.getCourseInfo(courseId)
        );
    }


}
