package com.github.backend.service.post;

import com.github.backend.dto.Post.PostRegisterOutPutDto;
import com.github.backend.dto.common.MemberInfo;
import com.github.backend.web.post.dto.PostReq;

public interface PostService {

    PostRegisterOutPutDto.Info registerPost(PostReq.Request request, MemberInfo memberInfo , String imagePath);
}
