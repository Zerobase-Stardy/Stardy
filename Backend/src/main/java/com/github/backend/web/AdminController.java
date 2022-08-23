package com.github.backend.web;

import com.github.backend.model.Result;
import com.github.backend.model.dto.*;
import com.github.backend.persist.entity.Admin;
import com.github.backend.persist.entity.Course;
import com.github.backend.persist.entity.Gamer;
import com.github.backend.persist.repository.querydsl.condition.GamerSearchCondition;
import com.github.backend.service.AdminService;
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
    public CreateAdmin.Response registerAdmin(
            @RequestBody @Valid CreateAdmin.Request request
    ){
        Admin admin = adminService.registerAdmin(
                request.getAdminId(),
                request.getPassword()
        );

        // convert Entity to DTO
        return new CreateAdmin.Response(
                admin.getAdminId(),
                admin.getPassword(),
                admin.getRole()
        );
    }

    @PostMapping("/gamer/register")
    public RegisterGamer.Response registerGamer(
            @RequestBody @Valid RegisterGamer.Request request
    ){
        Gamer gamer = adminService.registerGamer(
                request.getName(),
                request.getRace(),
                request.getNickname(),
                request.getIntroduce()
        );

        // convert Entity to DTO
        return new RegisterGamer.Response(
                gamer.getName(),
                gamer.getRace(),
                gamer.getNickname(),
                gamer.getIntroduce()
        );
    }

    @GetMapping("/gamer/list")
    public Result getGamerList(SearchGamer searchGamer){

        List<Gamer> gamer = adminService.getGamerList(searchGamer);

        return Result.builder()
                .status(200)
                .success(true)
                .data(gamer)
                .build();
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
                                gamer.getIntroduce()
                        ))
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
    public RegisterCourse.Response registerCourse(
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

        return new RegisterCourse.Response(
                course.getGamer().getName(),
                course.getTitle(),
                course.getRace(),
                course.getLevel(),
                course.getComment(),
                course.getPrice()
        );
    }

    @GetMapping("/course/{courseId}")
    public CourseInfoResponse getCourseInfo(
            @PathVariable("courseId") @Valid Long courseId
    ){
        return CourseInfoResponse.from(
                adminService.getCourseInfo(courseId)
        );
    }

    @PutMapping("/course/{courseId}/edit")
    public UpdateCourse.Response updateCourseInfo(
            @PathVariable("courseId") @Valid Long courseId,
            @RequestBody UpdateCourse.Request request
    ){

        Course course = adminService.updateCourseInfo(courseId, request);
        return new UpdateCourse.Response(
                course.getGamer().getName(),
                course.getTitle(),
                course.getVideoUrl(),
                course.getThumbnailUrl(),
                course.getComment(),
                course.getLevel(),
                course.getRace(),
                course.getPrice()
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
