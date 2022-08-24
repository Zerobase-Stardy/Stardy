package com.github.backend.web.course.controller;

import com.github.backend.dto.common.Result;
import com.github.backend.dto.course.CourseInfoOutputDto;
import com.github.backend.service.course.impl.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/course")
public class CourseController {

    private final CourseService courseService;

    @GetMapping("/{courseId}")
    public ResponseEntity<Result<?>> getCourseInfo(
            @PathVariable("courseId") @Valid Long courseId
    ){
        return ResponseEntity.ok()
                .body(
                        Result.builder()
                                .status(200)
                                .success(true)
                                .data(courseService.getCourseInfo(courseId))
                                .build()
                );
    }


}
