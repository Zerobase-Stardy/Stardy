package com.github.backend.web;

import com.github.backend.model.dto.*;
import com.github.backend.persist.entity.Admin;
import com.github.backend.persist.entity.Course;
import com.github.backend.persist.entity.Gamer;
import com.github.backend.service.AdminService;
import lombok.RequiredArgsConstructor;
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
                request.getNickName(),
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

    @GetMapping("/list")
    public SelectGamer.Response getGamerList(){

        List<Gamer> gamer = adminService.getGamerList();
        return new SelectGamer.Response(gamer);
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
}
