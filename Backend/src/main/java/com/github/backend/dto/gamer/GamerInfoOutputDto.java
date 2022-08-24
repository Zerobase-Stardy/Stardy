package com.github.backend.dto.gamer;

import com.github.backend.persist.gamer.Gamer;
import lombok.Builder;
import lombok.Data;

public class GamerInfoOutputDto {

    @Builder
    @Data
    public static class Info{
        private Long id;
        private String name;
        private String race;
        private String nickname;
        private String introduce;

        public static GamerInfoOutputDto.Info of(Gamer gamer){
            return Info.builder()
                    .id(gamer.getId())
                    .name(gamer.getName())
                    .race(gamer.getRace())
                    .nickname(gamer.getNickname())
                    .introduce(gamer.getIntroduce())
                    .build();
        }
    }
}
