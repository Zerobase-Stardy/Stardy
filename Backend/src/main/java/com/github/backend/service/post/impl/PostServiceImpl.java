package com.github.backend.service.post.impl;

import com.github.backend.dto.common.TokenMemberDto;
import com.github.backend.dto.post.*;
import com.github.backend.exception.post.PostException;
import com.github.backend.exception.post.code.PostErrorCode;
import com.github.backend.persist.member.Member;
import com.github.backend.persist.member.repository.MemberRepository;
import com.github.backend.persist.post.Post;
import com.github.backend.persist.post.repository.Impl.PostSearchRepository;
import com.github.backend.persist.post.repository.PostRepository;
import com.github.backend.service.post.PostService;
import com.github.backend.web.post.dto.PostReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final PostSearchRepository postSearchRepository;

    @Transactional
    @Override
    public PostUpdateOutPutDto.Info UpdatePost(Long postId, PostReq.Request request, String imagePath) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(PostErrorCode.POST_NOT_EXISTS));

        post.setImagePath(imagePath);
        post.setTitle(request.getTitle());
        post.setBoardKind(request.getBoardKind());
        post.setContent(request.getContent());


        return PostUpdateOutPutDto.Info.of(postRepository.save(post));
    }

    @Override
    public PostInfoOutPutDto.Info deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(PostErrorCode.POST_NOT_EXISTS));
        postRepository.delete(post);

        return PostInfoOutPutDto.Info.of((post));
    }

    @Override
    public List<PostListOutPutDto.Info> getTitleList(SearchTitle searchTitle) {
        return postSearchRepository.searchByWhere(searchTitle.toCondition())
                .stream()
                .map(PostListOutPutDto.Info::of)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public PostRegisterOutPutDto.Info registerPost(PostReq.Request req, TokenMemberDto.MemberInfo memberInfo, String imagePath) {
        Member member = memberRepository.findByEmail(memberInfo.getEmail())
                .orElseThrow(() -> new PostException(PostErrorCode.MEMBER_NOT_EXISTS));

        Post post = postRepository.save(Post.builder()
                .member(member)
                .title(req.getTitle())
                .content(req.getContent())
                .boardKind(req.getBoardKind())
                .imagePath(imagePath)
                .build());

        return  PostRegisterOutPutDto.Info.of(post);
    }

    @Override
    public PostInfoOutPutDto.Info getPostDetail(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(PostErrorCode.POST_NOT_EXISTS));
        return PostInfoOutPutDto.Info.of(post);
    }


}

