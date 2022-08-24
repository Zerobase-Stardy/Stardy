package com.github.backend.exception.admin;

import com.github.backend.exception.admin.code.AdminErrorCode;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminException extends RuntimeException{
    private AdminErrorCode errorCode;
    private String errorMessage;

    public AdminException(AdminErrorCode errorCode){
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDescription();
    }
}