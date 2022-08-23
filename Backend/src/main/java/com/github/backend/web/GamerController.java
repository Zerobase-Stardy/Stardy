package com.github.backend.web;

import com.github.backend.model.Result;
import com.github.backend.model.dto.SearchGamer;
import com.github.backend.persist.entity.Gamer;
import com.github.backend.service.GamerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
