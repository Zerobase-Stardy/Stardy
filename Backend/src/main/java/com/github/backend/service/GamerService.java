package com.github.backend.service;

import com.github.backend.exception.GamerException;
import com.github.backend.persist.entity.Gamer;
import com.github.backend.persist.repository.GamerRepository;
import com.github.backend.type.GamerErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GamerService {
    private final GamerRepository gamerRepository;

    @Transactional
    public Gamer registerGamer(
            String name,
            String race,
            String nickname,
            String introduce
    ){

        validateCreateGamer(nickname);

        return gamerRepository.save(
                Gamer.builder()
                        .name(name)
                        .race(race)
                        .nickName(nickname)
                        .introduce(introduce)
                        .build()
        );

    }

    @Transactional
    public List<Gamer> getGamerList(){
        return gamerRepository.findAll();
    }


    private void validateCreateGamer(String nickname) {
        if (gamerRepository.existsByNickName(nickname))
            throw new GamerException(GamerErrorCode.EXIST_SAME_GAMER_NICKNAME);
    }

}
