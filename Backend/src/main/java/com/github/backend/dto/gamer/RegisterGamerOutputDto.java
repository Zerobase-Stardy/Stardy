package com.github.backend.dto.gamer;

import com.github.backend.dto.admin.RegisterAdminOutputDto;
import com.github.backend.persist.gamer.Gamer;
import lombok.Builder;
import lombok.Data;

public class RegisterGamerOutputDto {

    @Builder
    @Data
    public static class Info{
        private String name;
        private String race;
        private String nickname;
        private String introduce;

        public static RegisterGamerOutputDto.Info of(Gamer gamer) {
            return RegisterGamerOutputDto.Info.builder()
                    .name(gamer.getName())
                    .race(gamer.getRace())
                    .nickname(gamer.getNickname())
                    .introduce(gamer.getIntroduce())
                    .build();
        }
    }
}
