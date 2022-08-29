package com.github.backend.dto.post;

import com.github.backend.persist.member.Member;
import com.github.backend.persist.post.Post;
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
        private String imagePath;
        private Member member;


        public static PostInfoOutPutDto.Info of(Post post){
            return Info.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .boardKind(post.getBoardKind())
                    .imagePath(post.getImagePath())
                    .member(post.getMember())
                    .build();
        }
    }
}
