package com.github.backend.web.post.controller;

import com.github.backend.dto.Post.*;
import com.github.backend.dto.common.MemberInfo;
import com.github.backend.dto.common.Result;
import com.github.backend.service.post.PostService;
import com.github.backend.service.post.impl.S3UploaderService;
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

    private final S3UploaderService s3UploaderService;
    private final PostService postService;

    @GetMapping("/post/{postId}")
    public ResponseEntity<Result<?>> postDetail(
            @PathVariable("postId") Long postId){

        PostInfoOutPutDto.Info postDetail = postService.getPostDetail(postId);

        return ResponseEntity.ok().body(
                Result.builder()
                        .status(200)
                        .success(true)
                        .data(postDetail)
                        .build()
        );
    }


    @PutMapping("/post/{postId}")
    public  ResponseEntity<Result<?>> updatePost(
            @PathVariable("postId") Long postId
            ,@RequestPart @Valid PostReq.Request request
            ,@RequestPart("image") MultipartFile multipartFile
    ) throws IOException {

        PostUpdateOutPutDto.Info update = postService.UpdatePost(
                postId,
                request,
                s3UploaderService.upload(multipartFile, "s3-stardy", "image"));

        return ResponseEntity.ok().body(
                Result.builder()
                        .status(200)
                        .success(true)
                        .data(update)
                        .build()
        );
    };

    @GetMapping("/posts")
    public ResponseEntity<Result<?>> getListPosts(SearchTitle searchTitle){
        List<PostListOutPutDto.Info> post = postService.getTitleList(searchTitle);

        return ResponseEntity.ok().body(
                Result.builder()
                        .status(200)
                        .success(true)
                        .data(post)
                        .build()
        );
    }


    @DeleteMapping("/post/{postId}")
    public ResponseEntity<Result<?>> deletePost(
            @PathVariable("postId") Long postId
    ){
        PostInfoOutPutDto.Info postInfo  = postService.deletePost(postId);

        return ResponseEntity.ok().body(
                Result.builder()
                        .status(200)
                        .success(true)
                        .data(postInfo)
                        .build()
        );
    }


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
