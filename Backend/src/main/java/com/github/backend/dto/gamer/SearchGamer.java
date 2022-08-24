package com.github.backend.dto.gamer;

import com.github.backend.persist.gamer.repository.querydsl.condition.GamerSearchCondition;
import lombok.*;


@Getter
@Builder
@AllArgsConstructor
public class SearchGamer {
    private String name;
    private String race;
    private String nickname;


    public GamerSearchCondition toCondition(){
        return GamerSearchCondition.builder()
                .name(getName())
                .race(getRace())
                .nickname(getNickname())
                .build();
    }
}
