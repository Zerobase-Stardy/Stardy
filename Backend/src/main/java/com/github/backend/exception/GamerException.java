package com.github.backend.exception;

import com.github.backend.type.GamerErrorCode;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GamerException extends RuntimeException{
    private GamerErrorCode errorCode;
    private String errorMessage;

    public GamerException(GamerErrorCode errorCode){
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDescription();
    }
}
