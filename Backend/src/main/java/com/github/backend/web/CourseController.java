package com.github.backend.web;

import com.github.backend.model.dto.CourseInfoResponse;
import com.github.backend.model.dto.RegisterCourse;
import com.github.backend.model.dto.UpdateCourse;
import com.github.backend.persist.entity.Course;
import com.github.backend.service.CourseService;
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
