package com.github.backend.dto.admin;

import com.github.backend.dto.attendance.AttendanceDto;
import com.github.backend.persist.admin.Admin;
import lombok.Builder;
import lombok.Data;

public class RegisterAdminOutputDto {

    @Builder
    @Data
    public static class Info{
        private String adminId;
        private String password;

        public static Info of(Admin admin) {
            return Info.builder()
                    .adminId(admin.getAdminId())
                    .password(admin.getPassword())
                    .build();
        }
    }



}
