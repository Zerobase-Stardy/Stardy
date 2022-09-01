package com.github.backend.web.course.controller;

import com.github.backend.dto.common.MemberInfo;
import com.github.backend.dto.common.Result;
import com.github.backend.dto.course.CourseInfoOutputDto;
import com.github.backend.dto.course.CourseInfoOutputDto.Info;
import com.github.backend.dto.course.SearchCourse;
import com.github.backend.service.course.impl.CourseService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<Result<?>> getCourseList(SearchCourse searchCourse, Pageable pageable) {

        Page<CourseInfoOutputDto.Info> courseList = courseService.searchCourseList(searchCourse,
            pageable);

        return ResponseEntity.ok().body(
            Result.builder()
                .status(200)
                .success(true)
                .data(courseList)
                .build()
        );
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<Result<?>> getMyCourse(@AuthenticationPrincipal MemberInfo memberInfo, @PathVariable Long courseId) {
        Info info = courseService.searchMyCourse(memberInfo.getId(), courseId);

        return ResponseEntity.ok().body(
            Result.builder()
                .status(200)
                .success(true)
                .data(info)
                .build()
        );
    }
}
