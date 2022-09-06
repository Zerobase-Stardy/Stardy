package com.github.backend.service.post;

import com.github.backend.dto.Post.*;
import com.github.backend.dto.common.MemberInfo;
import com.github.backend.web.post.dto.PostReq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {

    PostRegisterOutPutDto.Info registerPost(PostReq.Request request, MemberInfo memberInfo);
    PostInfoOutPutDto.Info getPostDetail(Long postId);
    PostUpdateOutPutDto.Info updatePost(Long postId, PostReq.Request request, MemberInfo memberInfo);
    PostInfoOutPutDto.Info deletePost(Long postId, MemberInfo memberInfo);
    Page<PostListOutPutDto.Info> getTitleList(SearchTitle searchTitle, Pageable pageable);
}
