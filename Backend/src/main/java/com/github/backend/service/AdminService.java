package com.github.backend.service;

import com.github.backend.exception.AdminException;
import com.github.backend.model.constants.Role;
import com.github.backend.persist.entity.Admin;
import com.github.backend.persist.repository.AdminRepository;
import com.github.backend.type.AdminErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;

    @Transactional
    public Admin registerAdmin(String adminId, String password){

        validateCreateAdmin(adminId);

        return adminRepository.save(
                Admin.builder()
                        .adminId(adminId)
                        .password(password)
                        .role(Role.ROLE_ADMIN)
                        .build()
        );
    }

    private void validateCreateAdmin(String adminId) {
        if(adminRepository.existsByAdminId(adminId))
            throw new AdminException(AdminErrorCode.EXIST_SAME_ADMIN_ID);
    }
}
