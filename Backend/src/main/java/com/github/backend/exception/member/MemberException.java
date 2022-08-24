package com.github.backend.exception.member;

import com.github.backend.exception.member.code.MemberErrorCode;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class MemberException extends RuntimeException{
    private MemberErrorCode errorCode;
    private String errorMessage;

    public MemberException(MemberErrorCode errorCode){
        super(errorCode.getDescription());
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDescription();
    }

    public MemberException(MemberErrorCode errorCode,Throwable cause) {
        super(errorCode.getDescription(),cause);
        this.errorCode = errorCode;
        this.errorMessage = getErrorMessage();
    }


}
