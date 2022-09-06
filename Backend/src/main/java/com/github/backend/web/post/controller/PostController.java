package com.github.backend.web.post.controller;

import com.github.backend.dto.Post.*;
import com.github.backend.dto.common.MemberInfo;
import com.github.backend.dto.common.Result;
import com.github.backend.service.post.PostService;
import com.github.backend.service.post.impl.S3UploadService;
import com.github.backend.web.post.dto.PostReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import springfox.documentation.annotations.ApiIgnore;

@Tag(name = "Post", description = "게시판 관련 API")
@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;
    private final S3UploadService s3UploadService;

    @Operation(
        summary = "게시글 상세조회", description = "게시글을 상세조회합니다.",
        tags = {"Post"}
    )
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


    @Operation(
        summary = "게시글 수정", description = "게시글을 수정합니다.",
        security = {@SecurityRequirement(name = "Authorization")},
        tags = {"Post"}
    )
    @Secured("ROLE_USER")
    @PutMapping("/posts/{postId}")
    public ResponseEntity<Result<?>> updatePost(
            @PathVariable("postId") Long postId
            ,@RequestBody @Valid PostReq.Request request
            ,@ApiIgnore @AuthenticationPrincipal MemberInfo memberInfo
    ) throws IOException {

        PostUpdateOutPutDto.Info update = postService.updatePost(
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

    @Operation(
        summary = "게시글 전체 조회", description = "게시글을 전체 조회합니다.",
        tags = {"Post"}
    )
    @GetMapping("/posts")
    public ResponseEntity<Result<?>> getListPosts(SearchTitle searchTitle, @PageableDefault(size=10, sort="id", direction = Sort.Direction.ASC)
    Pageable pageable) {
        Page<PostListOutPutDto.Info> post = postService.getTitleList(searchTitle, pageable);
        return ResponseEntity.ok().body(
                Result.builder()
                        .status(200)
                        .success(true)
                        .data(post)
                        .build()
        );
    }


    @Operation(
        summary = "게시글 삭제", description = "게시글을 삭제합니다.",
        security = {@SecurityRequirement(name = "Authorization")},
        tags = {"Post"}
    )
    @Secured("ROLE_USER")
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<Result<?>> deletePost(
            @PathVariable("postId") Long postId,
        @ApiIgnore @AuthenticationPrincipal MemberInfo memberInfo
    ) {
        PostInfoOutPutDto.Info postInfo = postService.deletePost(postId ,memberInfo);

        return ResponseEntity.ok().body(
                Result.builder()
                        .status(200)
                        .success(true)
                        .data(postInfo)
                        .build()
        );
    }

    @Operation(
        summary = "게시글 등록", description = "게시글을 등록합니다.",
        security = {@SecurityRequirement(name = "Authorization")},
        tags = {"Post"}
    )
    @Secured("ROLE_USER")
    @PostMapping("/posts")
    public ResponseEntity<Result<?>> registerPost(
            @ApiIgnore @AuthenticationPrincipal MemberInfo memberInfo,
            @RequestBody PostReq.Request request
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

    @Operation(
        summary = "이미지 저장", description = "이미지를 저장합니다.",
        security = {@SecurityRequirement(name = "Authorization")},
        tags = {"Post"}
    )
    @Secured("ROLE_USER")
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

    @Operation(
        summary = "이미지 삭제", description = "이미지를 삭제합니다.",
        security = {@SecurityRequirement(name = "Authorization")},
        tags = {"Post"}
    )
    @Secured("ROLE_USER")
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
