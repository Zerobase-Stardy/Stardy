package com.github.backend.dto.Post;


import com.github.backend.persist.member.Member;
import com.github.backend.persist.post.Post;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

public class PostInfoOutPutDto {
    @Builder
    @Data
    public static class Info{
        private Long id;
        private String title;
        private String content;
        private String boardKind;
        private LocalDateTime createdAt;
        private Member member;


        public static PostInfoOutPutDto.Info of(Post post){
            return Info.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .boardKind(post.getBoardKind())
                .createdAt(post.getCreatedDate())
                    .member(post.getMember())
                    .build();
        }
    }
}
