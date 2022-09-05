package com.github.backend.exception;

import com.github.backend.dto.common.ErrorResult;
import com.github.backend.exception.common.OAuthException;
import com.github.backend.exception.post.PostException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = PostException.class)
    public ResponseEntity<ErrorResult> postErrorhandler(PostException e){
        return ResponseEntity.badRequest().body(
                ErrorResult.builder()
                        .errorCode(e.getErrorCode().name())
                        .errorDescription(e.getErrorMessage())
                        .build()
        );
    }

    @ExceptionHandler(value = OAuthException.class)
    public ResponseEntity<ErrorResult> OAuthErrorhandler(OAuthException e){
        return ResponseEntity.badRequest().body(
                ErrorResult.builder()
                        .errorCode(e.getErrorCode().name())
                        .errorDescription(e.getErrorMessage())
                        .build()
        );
    }
}
