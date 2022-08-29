package com.github.backend.service.post;

import com.github.backend.dto.Post.PostInfoOutPutDto;
import com.github.backend.dto.Post.PostRegisterOutPutDto;
import com.github.backend.dto.Post.PostUpdateOutPutDto;
import com.github.backend.dto.common.MemberInfo;
import com.github.backend.web.post.dto.PostReq;

public interface PostService {

    PostRegisterOutPutDto.Info registerPost(PostReq.Request request, MemberInfo memberInfo , String imagePath);
    PostInfoOutPutDto.Info getPostDetail(Long postId);
    PostUpdateOutPutDto.Info UpdatePost(Long postId, PostReq.Request request, String imagePath);

    PostInfoOutPutDto.Info deletePost(Long postId);
}
