package com.github.backend.web.course.controller;

import com.github.backend.dto.common.Result;
import com.github.backend.dto.gamer.GamerInfoOutputDto;
import com.github.backend.dto.gamer.SearchGamer;
import com.github.backend.persist.gamer.Gamer;
import com.github.backend.service.gamer.impl.GamerService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gamer")
public class GamerController {

    private final GamerService gamerService;

    @GetMapping("/list")
    public ResponseEntity<Result<?>> getGamerList(
            SearchGamer searchGamer,
            @PageableDefault(size=10, sort="id", direction = Sort.Direction.ASC)
                    Pageable pageable)
    {

        Page<GamerInfoOutputDto.Info> gamer = gamerService.getGamerList(searchGamer, pageable);

        return ResponseEntity.ok().body(
                Result.builder()
                        .status(200)
                        .success(true)
                        .data(gamer)
                        .build()
        );
    }
}
