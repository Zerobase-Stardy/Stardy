package com.github.backend.web.admin;


import com.github.backend.dto.admin.LoginAdmin;
import com.github.backend.dto.common.AdminInfo;
import com.github.backend.dto.common.Result;
import com.github.backend.security.jwt.Tokens;
import com.github.backend.service.admin.impl.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminLoginController {

    private final AdminService adminService;

    @PostMapping("/login")
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
