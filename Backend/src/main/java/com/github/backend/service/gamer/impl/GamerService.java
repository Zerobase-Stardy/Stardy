package com.github.backend.service.gamer.impl;

import com.github.backend.dto.gamer.GamerInfoOutputDto;
import com.github.backend.dto.gamer.SearchGamer;
import com.github.backend.persist.gamer.Gamer;
import com.github.backend.persist.gamer.repository.GamerRepository;
import com.github.backend.persist.gamer.repository.querydsl.GamerSearchRepository;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GamerService {
    private final GamerSearchRepository gamerSearchRepository;

    /**
     * gamer 정보 반환
     * @param searchGamer : gamer 조회 Dto
     * gamer name, nickname, race 이용한 조회 가능
     */
    @Transactional
    public Page<GamerInfoOutputDto.Info> getGamerList(SearchGamer searchGamer, Pageable pageable){

        return gamerSearchRepository.searchByWhere(searchGamer.toCondition(), pageable)
                .map(GamerInfoOutputDto.Info::of);
    }

}
