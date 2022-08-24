package com.github.backend.service.gamer.impl;

import com.github.backend.dto.gamer.SearchGamer;
import com.github.backend.persist.gamer.Gamer;
import com.github.backend.persist.gamer.repository.GamerRepository;
import com.github.backend.persist.course.repository.querydsl.GamerSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GamerService {
    private final GamerRepository gamerRepository;
    private final GamerSearchRepository gamerSearchRepository;

    /**
     * gamer 정보 반환
     * @param searchGamer : gamer 조회 Dto
     * gamer name, nickname, race 이용한 조회 가능
     */
    @Transactional
    public List<Gamer> getGamerList(SearchGamer searchGamer){

        return gamerSearchRepository.searchByWhere(
                searchGamer.toCondition()
        );
    }

}
