package com.github.backend.web.post.postController;

import com.github.backend.dto.common.Result;
import com.github.backend.service.post.PostService;
import com.github.backend.service.post.impl.S3UploaderService;
import com.github.backend.web.post.dto.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class PostController {
    private final S3UploaderService s3UploaderService;
    private final PostService postService;

    @PostMapping("/post")
        public ResponseEntity<Result<?>> registerPost(@RequestPart @Valid Post.Request request, @RequestPart("image") MultipartFile multipartFile) throws IOException {
        postService.registerPost(
                request.getEmail(),
                request.getTitle(),
                request.getContent(),
                request.getBoardKind(),
                s3UploaderService.upload(multipartFile, "s3-stardy", "image"));

        return ResponseEntity.ok().body(new Result<>(200, true, null));
    }

}