package com.github.backend.web.admin;

import com.github.backend.dto.admin.CreateAdmin;
import com.github.backend.dto.admin.LoginAdmin;
import com.github.backend.dto.admin.RegisterAdminOutputDto;
import com.github.backend.dto.common.AdminInfo;
import com.github.backend.dto.common.Result;
import com.github.backend.dto.course.CourseInfoOutputDto;
import com.github.backend.dto.course.RegisterCourse;
import com.github.backend.dto.course.UpdateCourse;
import com.github.backend.dto.gamer.GamerInfoOutputDto;
import com.github.backend.dto.gamer.RegisterGamer;
import com.github.backend.dto.gamer.RegisterGamerOutputDto;
import com.github.backend.dto.gamer.SearchCourse;
import com.github.backend.dto.gamer.SearchGamer;
import com.github.backend.dto.gamer.UpdateGamer;
import com.github.backend.security.jwt.Tokens;
import com.github.backend.service.admin.impl.AdminService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminLoginController {

    private final AdminService adminService;

    @PostMapping("/login")
    public ResponseEntity<Result<?>> loginAdmin(@RequestBody @Valid LoginAdmin.Request request
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

    @GetMapping("/logout")
    public ResponseEntity<Result<?>> logout(@AuthenticationPrincipal AdminInfo adminInfo){

        return ResponseEntity.ok().body(
                Result.builder()
                        .status(200)
                        .success(true)
                        .data(adminService.logoutAdmin(adminInfo))
                        .build()
        );
    }

}
