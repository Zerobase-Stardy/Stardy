package com.github.backend.web.post.controller;

import com.github.backend.dto.Post.*;
import com.github.backend.dto.common.MemberInfo;
import com.github.backend.dto.common.Result;
import com.github.backend.service.post.PostService;
import com.github.backend.service.post.impl.S3UploadService;
import com.github.backend.web.post.dto.PostReq;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController

public class PostController {


    private final PostService postService;
    private final S3UploadService s3UploadService;

    @GetMapping("/posts/{postId}")
    public ResponseEntity<Result<?>> postDetail(
            @PathVariable("postId") Long postId) {

        PostInfoOutPutDto.Info postDetail = postService.getPostDetail(postId);

        return ResponseEntity.ok().body(
                Result.builder()
                        .status(200)
                        .success(true)
                        .data(postDetail)
                        .build()
        );
    }


    @PutMapping("/posts/{postId}")
    public ResponseEntity<Result<?>> updatePost(
            @PathVariable("postId") Long postId
            , @RequestPart @Valid PostReq.Request request
            ,@AuthenticationPrincipal MemberInfo memberInfo
    ) throws IOException {



        PostUpdateOutPutDto.Info update = postService.UpdatePost(
                postId,
                request,
                memberInfo);

        return ResponseEntity.ok().body(
                Result.builder()
                        .status(200)
                        .success(true)
                        .data(update)
                        .build()
        );
    }

    ;

    @GetMapping("/posts")
    public ResponseEntity<Result<?>> getListPosts(SearchTitle searchTitle) {
        List<PostListOutPutDto.Info> post = postService.getTitleList(searchTitle);

        return ResponseEntity.ok().body(
                Result.builder()
                        .status(200)
                        .success(true)
                        .data(post)
                        .build()
        );
    }


    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<Result<?>> deletePost(
            @PathVariable("postId") Long postId
    ) {
        PostInfoOutPutDto.Info postInfo = postService.deletePost(postId);

        return ResponseEntity.ok().body(
                Result.builder()
                        .status(200)
                        .success(true)
                        .data(postInfo)
                        .build()
        );
    }


    @PostMapping("/posts")
    public ResponseEntity<Result<?>> registerPost(
            @AuthenticationPrincipal MemberInfo memberInfo,
            @RequestPart @Valid PostReq.Request request,
            @RequestPart("image") MultipartFile multipartFile
    ) throws IOException {
        PostRegisterOutPutDto.Info postRegisterOutPutDto = postService.registerPost(
                request,
                memberInfo
        );
        return ResponseEntity.ok().body(
                Result.builder()
                        .status(200)
                        .success(true)
                        .data(postRegisterOutPutDto)
                        .build()
        );
    }

    @PostMapping("/postImage")
    public ResponseEntity<Result<?>> getImagePath(@RequestPart("image") MultipartFile multipartFile) throws IOException {
        String imagePath = s3UploadService.upload(multipartFile, "image").getPath();

        return ResponseEntity.ok().body(
                Result.builder()
                        .status(200)
                        .success(true)
                        .data(s3UploadService.upload(multipartFile, "image"))
                        .build()
        );
    }
    @DeleteMapping("/deleteImage")
    public ResponseEntity<Result<?>> deleteImage(@RequestParam String imageKey) throws IOException {
        s3UploadService.remove(imageKey);

        return ResponseEntity.ok().body(
                Result.builder()
                        .status(200)
                        .success(true)
                        .data(imageKey)
                        .build()
        );
    }
}
