package com.github.backend.exception.post;

import com.github.backend.exception.post.code.PostErrorCode;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostException extends RuntimeException {

    private PostErrorCode errorCode;
    private String errorMessage;

    public PostException(PostErrorCode errorCode) {
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDescription();
    }
}
