package com.github.backend.web.admin;


import com.github.backend.dto.admin.LoginAdmin;
import com.github.backend.dto.common.AdminInfo;
import com.github.backend.dto.common.Result;
import com.github.backend.security.jwt.Tokens;
import com.github.backend.service.admin.impl.AdminService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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


    @ApiOperation(
            value = "관리자 로그인.",
            notes = "관리자 로그인."
    )
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(
                            name ="adminId"
                            ,value ="관리자 ID"
                            ,required =true
                            ,dataType ="string"
                            ,paramType ="query"
                            ,defaultValue ="None"
                    ),

                    @ApiImplicitParam(
                            name = "password"
                            , value = "관리자 패스워드"
                            , required = true
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = "None"
                    )
            })
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


    @ApiOperation(
            value = "관리자 로그아웃.",
            notes = "가지고 있는 refresh Token 비활성화 시켜줍니다."
    )
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
