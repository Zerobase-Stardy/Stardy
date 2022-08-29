package com.github.backend.service.post;

import com.github.backend.dto.common.TokenMemberDto;
import com.github.backend.dto.post.*;
import com.github.backend.web.post.dto.PostReq;

import java.util.List;

public interface PostService {

    PostRegisterOutPutDto.Info registerPost(PostReq.Request request, TokenMemberDto.MemberInfo memberInfo ,String imagePath);
    PostInfoOutPutDto.Info getPostDetail(Long postId);
    PostUpdateOutPutDto.Info UpdatePost(Long postId, PostReq.Request request, String imagePath);
    PostInfoOutPutDto.Info deletePost(Long postId);

    List<PostListOutPutDto.Info> getTitleList(SearchTitle searchTitle);
}
