package com.github.backend.model.dto;

import com.github.backend.persist.repository.querydsl.condition.GamerSearchCondition;
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
