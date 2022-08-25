package com.github.backend.web.course.controller;

import com.github.backend.dto.common.Result;
import com.github.backend.dto.gamer.SearchGamer;
import com.github.backend.persist.gamer.Gamer;
import com.github.backend.service.gamer.impl.GamerService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gamer")
public class GamerController {

    private final GamerService gamerService;

    @GetMapping("/list")
    public Result getGamerList(SearchGamer searchGamer){

        List<Gamer> gamer = gamerService.getGamerList(searchGamer);

        return Result.builder()
                .status(200)
                .success(true)
                .data(gamer)
                .build();
    }
}
