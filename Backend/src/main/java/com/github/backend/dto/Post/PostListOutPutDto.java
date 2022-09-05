package com.github.backend.dto.Post;

import com.github.backend.persist.post.Post;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

public class PostListOutPutDto{

    @Builder
    @Data
    public static class Info{
        private Long id;
        private String title;
        private String writer;
        private String boardKind;
        private LocalDateTime createdAt;
        private String content;


        public static PostListOutPutDto.Info of(Post post){
            return Info.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .writer(post.getMember().getEmail())
                    .boardKind(post.getBoardKind())
                    .content(post.getContent())
                .createdAt(post.getCreatedDate())
                    .build();
        }
    }
}
