package com.github.backend.web;

import com.github.backend.model.dto.CreateAdmin;
import com.github.backend.persist.entity.Admin;
import com.github.backend.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/create")
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
}
