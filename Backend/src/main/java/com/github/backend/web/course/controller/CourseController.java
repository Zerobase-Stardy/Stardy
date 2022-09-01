package com.github.backend.web.course.controller;

import com.github.backend.dto.common.MemberInfo;
import com.github.backend.dto.common.Result;
import com.github.backend.dto.course.CourseInfoOutputDto;
import com.github.backend.dto.course.CourseInfoOutputDto.Info;
import com.github.backend.dto.course.SearchCourse;
import com.github.backend.service.course.impl.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Course")
@RestController
@RequiredArgsConstructor
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    @Operation(
        summary = "강의 전체 조회", description = "강의를 전체 조회 합니다.",
        tags = {"Course"}
    )
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

    @Operation(
        summary = "강의 조회", description = "강의를 조회합니다. 로그인 회원 및 구매한 강의만 조회 가능합니다.",
        security = {@SecurityRequirement(name = "Authorization")},
        tags = {"Course"}
    )
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
