package com.github.backend.service.post;

import com.github.backend.dto.Post.*;
import com.github.backend.dto.common.MemberInfo;
import com.github.backend.web.post.dto.PostReq;

import java.util.List;

public interface PostService {

    PostRegisterOutPutDto.Info registerPost(PostReq.Request request, MemberInfo memberInfo);
    PostInfoOutPutDto.Info getPostDetail(Long postId);
    PostUpdateOutPutDto.Info UpdatePost(Long postId, PostReq.Request request, MemberInfo memberInfo);
    PostInfoOutPutDto.Info deletePost(Long postId);
    List<PostListOutPutDto.Info> getTitleList(SearchTitle searchTitle);
}
