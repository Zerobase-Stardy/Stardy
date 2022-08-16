package com.github.backend.exception;

import com.github.backend.type.AdminErrorCode;
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