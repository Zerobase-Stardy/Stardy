package com.github.backend.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.github.backend.persist.entity.Member;
import com.github.backend.persist.entity.Post;
import com.github.backend.persist.repository.MemberRepository;
import com.github.backend.persist.repository.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PostServiceImplTest {

    @Mock
    MemberRepository memberRepository;

    @Mock
    PostRepository postRepository;

    @InjectMocks
    PostServiceImpl postService;

    @DisplayName("게시판 등록 성공")
    @Test
    void testRegisterPost(){
        //given
        Member member = Member.builder()
                .id(1L)
                .email("memberEmail")
                .build();

        Post post = Post.builder()
                .id(1L)
                .member(member)
                .title("post")
                .content("content")
                .boardKind("자유")
                .build();

        given(memberRepository.findByEmail(anyString()))
                .willReturn(Optional.of(member));

        given(postRepository.save(any()))
                .willReturn(post);

        ArgumentCaptor<Post> captor = ArgumentCaptor.forClass(
                Post.class);
        //when
        postService.registerPost(
                member.getEmail(),
                post.getTitle(),
                post.getContent(),
                post.getBoardKind()
        );

        //then
        verify(postRepository).save(captor.capture());
        assertThat(captor.getValue().getMember().getEmail()).isEqualTo(member.getEmail());
        assertThat(captor.getValue().getTitle()).isEqualTo(post.getTitle());
        assertThat(captor.getValue().getContent()).isEqualTo(post.getContent());
        assertThat(captor.getValue().getBoardKind()).isEqualTo(post.getBoardKind());
    }
}
