package com.github.backend.web;

import com.github.backend.model.dto.CreateAdmin;
import com.github.backend.model.dto.RegisterGamer;
import com.github.backend.model.dto.SelectGamer;
import com.github.backend.persist.entity.Admin;
import com.github.backend.persist.entity.Gamer;
import com.github.backend.service.AdminService;
import com.github.backend.service.GamerService;
import jdk.vm.ci.code.Register;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gamer")
public class GamerController {

    private final GamerService gamerService;

    @PostMapping("/register")
    public RegisterGamer.Response registerGamer(
            @RequestBody @Valid RegisterGamer.Request request
    ){
        Gamer gamer = gamerService.registerGamer(
                request.getName(),
                request.getRace(),
                request.getNickName(),
                request.getIntroduce()
        );

        // convert Entity to DTO
        return new RegisterGamer.Response(
                gamer.getName(),
                gamer.getRace(),
                gamer.getNickName(),
                gamer.getIntroduce()
        );
    }

    @GetMapping("/get-list")
    public SelectGamer.Response getGamerList(){

        List<Gamer> gamer = gamerService.getGamerList();
        return new SelectGamer.Response(gamer);
    }
}
