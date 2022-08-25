package com.github.backend.dto.admin;

import com.github.backend.dto.common.AdminInfo;
import lombok.Builder;
import lombok.Data;


public class LogoutAdminOutputDto {
    private String adminId;

    @Builder
    @Data
    public static class Info{
        private String adminId;

        public static LogoutAdminOutputDto.Info of(AdminInfo adminInfo) {
            return LogoutAdminOutputDto.Info.builder()
                    .adminId(adminInfo.getEmail())
                    .build();
        }
    }
}
