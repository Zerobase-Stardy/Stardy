package com.github.backend.persist.repository.condition;

import com.github.backend.model.constants.GamerSearchType;
import lombok.Data;

@Data
public class GamerSearchCondition {
    String content;
    GamerSearchType gamerSearchType;

    public GamerSearchCondition(String content, GamerSearchType gamerSearchType){
        this.content = content;
        this.gamerSearchType = gamerSearchType;
    }
}
