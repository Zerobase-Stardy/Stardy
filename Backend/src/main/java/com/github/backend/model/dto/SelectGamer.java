package com.github.backend.model.dto;

import com.github.backend.persist.entity.Gamer;
import lombok.*;

import java.util.List;

public class SelectGamer {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response{
        private List<Gamer> gamerList;
    }
}
