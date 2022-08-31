package com.github.backend.service.post.impl;

import com.github.backend.dto.Post.*;
import com.github.backend.dto.common.MemberInfo;
import com.github.backend.exception.post.PostException;
import com.github.backend.exception.post.code.PostErrorCode;
import com.github.backend.persist.member.Member;
import com.github.backend.persist.member.repository.MemberRepository;
import com.github.backend.persist.post.Post;
import com.github.backend.persist.post.repository.PostRepository;
import com.github.backend.persist.post.repository.PostSearchRepository;
import com.github.backend.service.post.PostService;
import com.github.backend.web.post.dto.PostReq;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    private final PostSearchRepository postSearchRepository;

    @Transactional
    @Override
    public PostUpdateOutPutDto.Info updatePost(Long postId, PostReq.Request request, MemberInfo memberInfo) {


        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(PostErrorCode.POST_NOT_EXISTS));


        if(post.getMember().getId() != memberInfo.getId()){
            throw new PostException(PostErrorCode.POST_NOT_EQ_MEMBER);
        }

        post.update(request.getTitle(), request.getContent(), request.getBoardKind());

        return PostUpdateOutPutDto.Info.of(postRepository.save(post));
    }

    @Transactional
    @Override
    public PostRegisterOutPutDto.Info registerPost(PostReq.Request req, MemberInfo memberInfo) {
        Member member = memberRepository.findByEmail(memberInfo.getEmail())
                .orElseThrow(() -> new PostException(PostErrorCode.MEMBER_NOT_EXISTS));

        Post post = postRepository.save(Post.builder()
                .member(member)
                .title(req.getTitle())
                .content(req.getContent())
                .boardKind(req.getBoardKind())
                .build());

        return  PostRegisterOutPutDto.Info.of(post);
    }

    @Override
    public PostInfoOutPutDto.Info deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(PostErrorCode.POST_NOT_EXISTS));
        postRepository.delete(post);

        return PostInfoOutPutDto.Info.of((post));
    }

    @Override
    public Page<PostListOutPutDto.Info> getTitleList(SearchTitle searchTitle, Pageable pageable) {
        return postSearchRepository.searchByWhere(searchTitle.toCondition(),pageable).
                map(PostListOutPutDto.Info::of);

    }


    @Override
    public PostInfoOutPutDto.Info getPostDetail(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(PostErrorCode.POST_NOT_EXISTS));
        return PostInfoOutPutDto.Info.of(post);
    }
}
