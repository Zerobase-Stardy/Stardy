package com.github.backend.exception.course;

import com.github.backend.exception.course.code.CourseErrorCode;
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
