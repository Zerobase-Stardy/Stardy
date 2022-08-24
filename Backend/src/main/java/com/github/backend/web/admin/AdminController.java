package com.github.backend.web;

import com.github.backend.dto.course.CourseInfoResponse;
import com.github.backend.dto.admin.CreateAdmin;
import com.github.backend.dto.course.RegisterCourse;
import com.github.backend.dto.gamer.RegisterGamer;
import com.github.backend.dto.gamer.SearchCourse;
import com.github.backend.dto.gamer.SearchGamer;
import com.github.backend.dto.course.UpdateCourse;
import com.github.backend.dto.gamer.UpdateGamer;
import com.github.backend.dto.common.Result;
import com.github.backend.model.dto.*;
import com.github.backend.persist.admin.Admin;
import com.github.backend.persist.course.Course;
import com.github.backend.persist.gamer.Gamer;
import com.github.backend.service.admin.impl.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/register")
    public ResponseEntity<Result<?>> registerAdmin(
            @RequestBody @Valid CreateAdmin.Request request
    ){
        Admin admin = adminService.registerAdmin(
                request.getAdminId(),
                request.getPassword()
        );

        // convert Entity to DTO
        return ResponseEntity.ok().body(
                Result.builder()
                        .status(200)
                        .success(true)
                        .data(
                                new CreateAdmin.Response(
                                        admin.getAdminId(),
                                        admin.getPassword(),
                                        admin.getRole())
                        )
                        .build()
        );
    }

    @PostMapping("/gamer/register")
    public ResponseEntity<Result<?>> registerGamer(
            @RequestBody @Valid RegisterGamer.Request request
    ){
        Gamer gamer = adminService.registerGamer(
                request.getName(),
                request.getRace(),
                request.getNickname(),
                request.getIntroduce()
        );

        // convert Entity to DTO
        return ResponseEntity.ok().body(
                Result.builder()
                        .status(200)
                        .success(true)
                        .data(
                                new RegisterGamer.Response(
                                        gamer.getName(),
                                        gamer.getRace(),
                                        gamer.getNickname(),
                                        gamer.getIntroduce())
                        )
                        .build()
        );
    }
    @GetMapping("/gamer/{gamerId}")
    public ResponseEntity<Result<?>> getGamerInfo(
            @PathVariable("gamerId") Long gamerId){

        Gamer gamer = adminService.getGamerInfo(gamerId);

        return ResponseEntity.ok().body(
                Result.builder()
                        .status(200)
                        .success(true)
                        .data(gamer)
                        .build()
        );
    }

    @GetMapping("/gamer/list")
    public ResponseEntity<Result<?>> getGamerList(SearchGamer searchGamer){

        List<Gamer> gamer = adminService.getGamerList(searchGamer);

        return ResponseEntity.ok().body(
                Result.builder()
                        .status(200)
                        .success(true)
                        .data(gamer)
                        .build()
        );
    }

    @PutMapping("/gamer/{gamerId}")
    public ResponseEntity<Result<?>> updateGamerInfo(
            @PathVariable  Long gamerId,
            @RequestBody @Valid UpdateGamer.Request request
    ){
        Gamer gamer = adminService.updateGamer(
                    gamerId,
                request.getName(),
                request.getRace(),
                request.getNickname(),
                request.getIntroduce()
        );

        return ResponseEntity.ok().body(
                Result.builder()
                        .status(200)
                        .success(true)
                        .data(
                                new UpdateGamer.Response(
                                gamer.getName(),
                                gamer.getRace(),
                                gamer.getNickname(),
                                gamer.getIntroduce())
                        )
                        .build()
        );
    }

    @DeleteMapping("/gamer/{gamerId}")
    public ResponseEntity<Result<?>> deleteGamerInfo(
            @PathVariable("gamerId") @Valid Long gamerId
    ){
        adminService.deleteGamer(gamerId);

        return ResponseEntity.ok().body(null);
    }



    @PostMapping("/course/register")
    public ResponseEntity<Result<?>> registerCourse(
            @RequestBody @Valid RegisterCourse.Request request
    ){
        Course course = adminService.registerCourse(
                request.getGamerId(),
                request.getTitle(),
                request.getVideoUrl(),
                request.getThumbnailUrl(),
                request.getComment(),
                request.getLevel(),
                request.getRace(),
                request.getPrice()
        );

        return ResponseEntity.ok().body(
                Result.builder()
                        .status(200)
                        .success(true)
                        .data(
                                new RegisterCourse.Response(
                                course.getGamer().getName(),
                                course.getTitle(),
                                course.getRace(),
                                course.getLevel(),
                                course.getComment(),
                                course.getPrice())
                        )
                        .build()
        );
    }

    @GetMapping("/course/list")
    public ResponseEntity<Result<?>> getCourseList(SearchCourse searchCourse){

        List<Course> Course = adminService.searchCourseList(searchCourse);

        return ResponseEntity.ok().body(
                Result.builder()
                        .status(200)
                        .success(true)
                        .data(Course)
                        .build()
        );
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<Result<?>> getCourseInfo(
            @PathVariable("courseId") @Valid Long courseId
    ){
        return ResponseEntity.ok()
                .body(
                    Result.builder()
                            .status(200)
                            .success(true)
                            .data(
                                    CourseInfoResponse.from(
                                            adminService.getCourseInfo(courseId)))
                            .build()
                );
    }

    @PutMapping("/course/{courseId}/edit")
    public ResponseEntity<Result<?>> updateCourseInfo(
            @PathVariable("courseId") @Valid Long courseId,
            @RequestBody UpdateCourse.Request request
    ){

        Course course = adminService.updateCourseInfo(courseId, request);
        return ResponseEntity.ok()
                .body(
                        Result.builder()
                                .status(200)
                                .success(true)
                                .data(
                                        new UpdateCourse.Response(
                                            course.getGamer().getName(),
                                            course.getTitle(),
                                            course.getVideoUrl(),
                                            course.getThumbnailUrl(),
                                            course.getComment(),
                                            course.getLevel(),
                                            course.getRace(),
                                            course.getPrice())
                                )
                                .build()
                );
    }

    @DeleteMapping("/course/{courseId}")
    public ResponseEntity<Result<?>> deleteCourseInfo(
            @PathVariable("courseId") @Valid Long courseId
    ){
        adminService.deleteCourse(courseId);

        return ResponseEntity.ok().body(null);
    }

}
