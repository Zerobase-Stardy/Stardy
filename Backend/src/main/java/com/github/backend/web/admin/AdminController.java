package com.github.backend.web.admin;

import com.github.backend.dto.admin.LoginAdmin;
import com.github.backend.dto.admin.RegisterAdminOutputDto;
import com.github.backend.dto.common.Tokens;
import com.github.backend.dto.course.CourseInfoOutputDto;
import com.github.backend.dto.admin.CreateAdmin;
import com.github.backend.dto.course.RegisterCourse;
import com.github.backend.dto.gamer.*;
import com.github.backend.dto.course.UpdateCourse;
import com.github.backend.dto.common.Result;
import com.github.backend.service.admin.impl.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin-management")
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/admin")
    public ResponseEntity<Result<?>> registerAdmin(
            @RequestBody @Valid CreateAdmin.Request request
    ){
        RegisterAdminOutputDto.Info adminInfo = adminService.registerAdmin(
                request.getAdminId(),
                request.getPassword()
        );

        // convert Entity to DTO
        return ResponseEntity.ok().body(
                Result.builder()
                        .status(200)
                        .success(true)
                        .data(adminInfo)
                        .build()
        );
    }


    @PostMapping("/admin/login")
    public ResponseEntity<Result<?>> loginAdmin(
            @RequestBody @Valid LoginAdmin.Request request
    ){
        Tokens tokens = adminService.loginAdmin(
                request.getAdminId(), request.getPassword()
        );

        // convert Entity to DTO
        return ResponseEntity.ok().body(
                Result.builder()
                        .status(200)
                        .success(true)
                        .data(tokens)
                        .build()
        );
    }

    @PostMapping("/gamer")
    public ResponseEntity<Result<?>> registerGamer(
            @RequestBody @Valid RegisterGamer.Request request
    ){
        RegisterGamerOutputDto.Info gamerInfo = adminService.registerGamer(request);

        // convert Entity to DTO
        return ResponseEntity.ok().body(
                Result.builder()
                        .status(200)
                        .success(true)
                        .data(gamerInfo)
                        .build()
        );
    }
    @GetMapping("/gamer/{gamerId}")
    public ResponseEntity<Result<?>> getGamerInfo(
            @PathVariable("gamerId") Long gamerId){

        GamerInfoOutputDto.Info gamerInfo = adminService.getGamerInfo(gamerId);

        return ResponseEntity.ok().body(
                Result.builder()
                        .status(200)
                        .success(true)
                        .data(gamerInfo)
                        .build()
        );
    }

    @GetMapping("/gamers")
    public ResponseEntity<Result<?>> getGamerList(SearchGamer searchGamer){

        List<GamerInfoOutputDto.Info> gamersInfo = adminService.getGamerList(searchGamer);

        return ResponseEntity.ok().body(
                Result.builder()
                        .status(200)
                        .success(true)
                        .data(gamersInfo)
                        .build()
        );
    }

    @PutMapping("/gamer/{gamerId}")
    public ResponseEntity<Result<?>> updateGamerInfo(
            @PathVariable  Long gamerId,
            @RequestBody @Valid UpdateGamer.Request request
    ){
        GamerInfoOutputDto.Info gamerInfo = adminService.updateGamer(
                gamerId,
                request
        );

        return ResponseEntity.ok().body(
                Result.builder()
                        .status(200)
                        .success(true)
                        .data(gamerInfo)
                        .build()
        );
    }

    @DeleteMapping("/gamer/{gamerId}")
    public ResponseEntity<Result<?>> deleteGamerInfo(
            @PathVariable("gamerId") @Valid Long gamerId
    ){
        GamerInfoOutputDto.Info gamerInfo = adminService.deleteGamer(gamerId);

        return ResponseEntity.ok().body(
                Result.builder()
                        .status(200)
                        .success(true)
                        .data(gamerInfo)
                        .build()
        );
    }



    @PostMapping("/course")
    public ResponseEntity<Result<?>> registerCourse(
            @RequestBody @Valid RegisterCourse.Request request
    ){
        CourseInfoOutputDto.Info courseInfo = adminService.registerCourse(request);

        return ResponseEntity.ok().body(
                Result.builder()
                        .status(200)
                        .success(true)
                        .data(courseInfo)
                        .build()
        );
    }

    @GetMapping("/courses")
    public ResponseEntity<Result<?>> getCourseList(SearchCourse searchCourse){

        List<CourseInfoOutputDto.Info> Course = adminService.searchCourseList(searchCourse);

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
                            .data(adminService.getCourseInfo(courseId))
                            .build()
                );
    }

    @PutMapping("/course/{courseId}")
    public ResponseEntity<Result<?>> updateCourseInfo(
            @PathVariable("courseId") @Valid Long courseId,
            @RequestBody UpdateCourse.Request request
    ){

        CourseInfoOutputDto.Info courseInfo = adminService.updateCourseInfo(courseId, request);
        return ResponseEntity.ok()
                .body(
                        Result.builder()
                                .status(200)
                                .success(true)
                                .data(courseInfo)
                                .build()
                );
    }

    @DeleteMapping("/course/{courseId}")
    public ResponseEntity<Result<?>> deleteCourseInfo(
            @PathVariable("courseId") @Valid Long courseId
    ){
        CourseInfoOutputDto.Info courseInfo = adminService.deleteCourse(courseId);

        return ResponseEntity.ok().body(
                Result.builder()
                        .status(200)
                        .success(true)
                        .data(courseInfo)
                        .build()
        );
    }

}
