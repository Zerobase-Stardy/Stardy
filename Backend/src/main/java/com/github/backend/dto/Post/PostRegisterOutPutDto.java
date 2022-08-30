package com.github.backend.dto.Post;


import com.github.backend.persist.member.Member;
import com.github.backend.persist.post.Post;
import lombok.Builder;
import lombok.Data;

public class PostRegisterOutPutDto {
    @Builder
    @Data
    public static class Info{
        private String title;
        private String content;
        private String boardKind;
        private String imagePath;
        private Member member;


        public static PostRegisterOutPutDto.Info of(Post post){
            return Info.builder()
                    .title(post.getTitle())
                    .content(post.getContent())
                    .boardKind(post.getBoardKind())
                    .imagePath(post.getImagePath())
                    .member(post.getMember())
                    .build();
        }
    }
}
