package com.github.backend.persist.repository.querydsl.condition;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GamerSearchCondition {
    String name;
    String nickname;
    String race;

    public GamerSearchCondition(String name, String nickname, String race){
        this.name = name;
        this.nickname = nickname;
        this.race = race;
    }
}
