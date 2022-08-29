package com.github.backend.web.post.controller;

import com.github.backend.dto.Post.PostRegisterOutPutDto;
import com.github.backend.dto.common.MemberInfo;
import com.github.backend.dto.common.Result;
import com.github.backend.service.post.PostService;
import com.github.backend.service.post.impl.S3UploaderService;
import com.github.backend.web.post.dto.PostReq;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseEntity<Result<?>> registerPost(
            @AuthenticationPrincipal MemberInfo memberInfo,
            @RequestPart @Valid PostReq.Request request,
            @RequestPart("image") MultipartFile multipartFile
    ) throws IOException {
        PostRegisterOutPutDto.Info postRegisterOutPutDto = postService.registerPost(
                request,
                memberInfo,
                s3UploaderService.upload(multipartFile, "s3-stardy", "image"));

        return ResponseEntity.ok().body(
                Result.builder()
                        .status(200)
                        .success(true)
                        .data(postRegisterOutPutDto)
                        .build()
        );
    }
}
