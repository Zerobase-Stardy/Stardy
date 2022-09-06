package com.github.backend.exception;

import com.github.backend.dto.common.ErrorResult;
import com.github.backend.exception.admin.AdminException;
import com.github.backend.exception.attendance.AttendanceException;
import com.github.backend.exception.common.JwtInvalidException;
import com.github.backend.exception.common.OAuthException;
import com.github.backend.exception.course.CourseException;
import com.github.backend.exception.gamer.GamerException;
import com.github.backend.exception.member.MemberException;
import com.github.backend.exception.myCourse.MyCourseException;
import com.github.backend.exception.post.PostException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = AdminException.class)
    public ResponseEntity<ErrorResult> adminExceptionHandler(AdminException e) {
        return ResponseEntity.badRequest().body(
                ErrorResult.builder()
                        .errorCode(e.getErrorCode().name())
                        .errorDescription(e.getErrorMessage())
                        .build()
        );
    }

    @ExceptionHandler(value = AttendanceException.class)
    public ResponseEntity<ErrorResult> attendanceExceptionHandler(AttendanceException e) {
        return ResponseEntity.badRequest().body(
                ErrorResult.builder()
                        .errorCode(e.getErrorCode().name())
                        .errorDescription(e.getErrorMessage())
                        .build()
        );
    }

    @ExceptionHandler(value = JwtInvalidException.class)
    public ResponseEntity<ErrorResult> JwtInvalidExceptionHandler(JwtInvalidException e) {
        return ResponseEntity.badRequest().body(
                ErrorResult.builder()
                        .errorCode(e.getErrorCode().name())
                        .errorDescription(e.getErrorMessage())
                        .build()
        );
    }

    @ExceptionHandler(value = OAuthException.class)
    public ResponseEntity<ErrorResult> OAuthExceptionHandler(OAuthException e) {
        return ResponseEntity.badRequest().body(
                ErrorResult.builder()
                        .errorCode(e.getErrorCode().name())
                        .errorDescription(e.getErrorMessage())
                        .build()
        );
    }

    @ExceptionHandler(value = CourseException.class)
    public ResponseEntity<ErrorResult> CourseExceptionHandler(CourseException e) {
        return ResponseEntity.badRequest().body(
                ErrorResult.builder()
                        .errorCode(e.getErrorCode().name())
                        .errorDescription(e.getErrorMessage())
                        .build()
        );
    }

    @ExceptionHandler(value = GamerException.class)
    public ResponseEntity<ErrorResult> GamerExceptionHandler(GamerException e) {
        return ResponseEntity.badRequest().body(
                ErrorResult.builder()
                        .errorCode(e.getErrorCode().name())
                        .errorDescription(e.getErrorMessage())
                        .build()
        );
    }

    @ExceptionHandler(value = MemberException.class)
    public ResponseEntity<ErrorResult> MemberExceptionHandler(MemberException e) {
        return ResponseEntity.badRequest().body(
                ErrorResult.builder()
                        .errorCode(e.getErrorCode().name())
                        .errorDescription(e.getErrorMessage())
                        .build()
        );
    }

    @ExceptionHandler(value = MyCourseException.class)
    public ResponseEntity<ErrorResult> MyCourseExceptionHandler(MyCourseException e) {
        return ResponseEntity.badRequest().body(
                ErrorResult.builder()
                        .errorCode(e.getErrorCode().name())
                        .errorDescription(e.getErrorMessage())
                        .build()
        );
    }

    @ExceptionHandler(value = PostException.class)
    public ResponseEntity<ErrorResult> PostExceptionHandler(PostException e) {
        return ResponseEntity.badRequest().body(
                ErrorResult.builder()
                        .errorCode(e.getErrorCode().name())
                        .errorDescription(e.getErrorMessage())
                        .build()
        );
    }

}
