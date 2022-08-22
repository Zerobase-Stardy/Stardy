package com.github.backend.exception;

import com.github.backend.persist.entity.Member;
import com.github.backend.type.AdminErrorCode;
import com.github.backend.type.MemberErrorCode;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class MemberException {
    private MemberErrorCode errorCode;
    private String errorMessage;

    public MemberException(MemberErrorCode errorCode){
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDescription();
    }
}
