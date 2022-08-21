package com.github.backend.service;

import com.github.backend.persist.entity.Gamer;
import com.github.backend.persist.repository.GamerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GamerService {
    private final GamerRepository gamerRepository;

    @Transactional
    public List<Gamer> getGamerList(){
        return gamerRepository.findAll();
    }

}
