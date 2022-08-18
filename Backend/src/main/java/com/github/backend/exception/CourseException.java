package com.github.backend.exception;

import com.github.backend.type.CourseErrorCode;
import com.github.backend.type.GamerErrorCode;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseException extends RuntimeException{
    private CourseErrorCode errorCode;
    private String errorMessage;

    public CourseException(CourseErrorCode errorCode){
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDescription();
    }

}
