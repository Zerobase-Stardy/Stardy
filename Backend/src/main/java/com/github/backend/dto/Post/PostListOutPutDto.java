package com.github.backend.dto.Post;

import com.github.backend.persist.post.Post;
import lombok.Builder;
import lombok.Data;

public class PostListOutPutDto{

    @Builder
    @Data
    public static class Info{
        private Long id;
        private String title;
        private String writer;


        public static PostListOutPutDto.Info of(Post post){
            return PostListOutPutDto.Info.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .writer(post.getMember().getEmail())
                    .build();
        }
    }
}
