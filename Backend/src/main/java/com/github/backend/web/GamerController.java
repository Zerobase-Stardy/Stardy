package com.github.backend.web;

import com.github.backend.model.dto.RegisterGamer;
import com.github.backend.model.dto.SelectGamer;
import com.github.backend.persist.entity.Gamer;
import com.github.backend.service.GamerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gamer")
public class GamerController {

    private final GamerService gamerService;

    @GetMapping("/list")
    public SelectGamer.Response getGamerList(){

        List<Gamer> gamer = gamerService.getGamerList();
        return new SelectGamer.Response(gamer);
    }
}
