package com.github.backend.admin.service;

import com.github.backend.exception.AdminException;
import com.github.backend.model.constants.Role;
import com.github.backend.persist.entity.Admin;
import com.github.backend.persist.repository.AdminRepository;
import com.github.backend.service.AdminService;
import com.github.backend.type.AdminErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {

    @Mock
    private AdminRepository adminRepository;

    @InjectMocks
    private AdminService adminService;

    @Test
    @DisplayName("관리자 계정 생성 성공")
    void testCreateAdmin(){
        //given
        given(adminRepository.save(any()))
                .willReturn(
                        Admin.builder()
                                .adminId("admin1234")
                                .password("password")
                                .role(Role.ROLE_ADMIN)
                                .build()
                );
        //when
        Admin compareAdmin = adminService.registerAdmin(
                "admin1234","password"
        );

        //then
        assertEquals(compareAdmin.getAdminId(), "admin1234");
        assertEquals(compareAdmin.getPassword(), "password");
        assertEquals(compareAdmin.getRole(), Role.ROLE_ADMIN);
    }

    @Test
    @DisplayName("관리자 계정 생성 실패 - 중복 사용자")
    void testCreateAdminFailed(){
        //given
        given(adminRepository.existsByAdminId(anyString()))
                .willReturn(true);
        //when
        AdminException adminException = assertThrows(AdminException.class,
                () -> adminService.registerAdmin("admin1234","password"));

        //then
        assertEquals(AdminErrorCode.EXIST_SAME_ADMIN_ID.getDescription(), adminException.getErrorCode().getDescription());
    }
}
