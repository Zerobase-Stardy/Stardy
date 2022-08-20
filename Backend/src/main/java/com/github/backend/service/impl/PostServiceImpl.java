package com.github.backend.service.impl;

import com.github.backend.exception.PostException;
import com.github.backend.persist.entity.Member;
import com.github.backend.persist.entity.Post;
import com.github.backend.persist.repository.MemberRepository;
import com.github.backend.persist.repository.PostRepository;
import com.github.backend.service.PostService;
import com.github.backend.type.PostErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public void registerPost(String email, String title, String content, String boardKind) {

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new PostException(PostErrorCode.MEMBER_NOT_EXISTS));

        postRepository.save(Post.builder()
                        .member(member)
                        .title(title)
                        .content(content)
                        .boardKind(boardKind)
                .build());
    }
}
