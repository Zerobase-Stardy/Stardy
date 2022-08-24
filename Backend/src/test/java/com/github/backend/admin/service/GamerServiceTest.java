package com.github.backend.admin.service;

import com.github.backend.dto.gamer.SearchGamer;
import com.github.backend.persist.gamer.Gamer;
import com.github.backend.persist.gamer.repository.GamerRepository;
import com.github.backend.persist.course.repository.querydsl.GamerSearchRepository;
import com.github.backend.service.gamer.impl.GamerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class GamerServiceTest {

    @Mock
    private GamerRepository gamerRepository;

    @Mock
    private GamerSearchRepository gamerSearchRepository;

    @InjectMocks
    private GamerService gamerService;

    @Test
    @DisplayName("게이머 목록 조회 성공")
    void testGetGamerList(){
        //given
        Gamer gamer = Gamer.builder()
                .name("유영진")
                .race("테란")
                .nickname("rush")
                .introduce("단단한 테란")
                .build();

        List<Gamer> gamers = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            gamers.add(gamer);
        }

        SearchGamer searchGamer = SearchGamer.builder()
                .name(gamer.getName())
                .race(gamer.getRace())
                .nickname(gamer.getNickname())
                .build();

        given(gamerSearchRepository.searchByWhere(any()))
                .willReturn(gamers);

        //when
        List<Gamer> findGamerList = gamerService.getGamerList(searchGamer);

        //then
        assertEquals(2, findGamerList.size());
        assertEquals(gamer.getName(), findGamerList.get(0).getName());
        assertEquals(gamer.getNickname(), findGamerList.get(0).getNickname());
        assertEquals(gamer.getRace(), findGamerList.get(0).getRace());
        assertEquals(gamer.getIntroduce(), findGamerList.get(0).getIntroduce());
    }

    @Test
    @DisplayName("게이머 이름 검색 조회 성공")
    void testGetGamerNameList(){
        //given
        List<Gamer> gamers = new ArrayList<>();
        Gamer gamer = Gamer.builder()
                .name("유영진")
                .build();

        for (int i = 0; i < 2; i++) {
            gamers.add(gamer);
        }
        given(gamerSearchRepository.searchByWhere(any()))
                .willReturn(gamers);

        SearchGamer searchGamer = SearchGamer.builder()
                .name(gamer.getName())
                .build();

        //when
        List<Gamer> gamerNameList = gamerService.getGamerList(searchGamer);

        //then
        assertEquals(gamerNameList.size(), 2);
        assertEquals(gamerNameList.get(0).getName(), gamer.getName());
    }

    @Test
    @DisplayName("게이머 종족 검색 조회 성공")
    void testGetGamerRaceList(){
        //given
        List<Gamer> gamers = new ArrayList<>();
        Gamer gamer = Gamer.builder()
                .race("테란")
                .build();

        for (int i = 0; i < 2; i++) {
            gamers.add(gamer);
        }
        SearchGamer searchGamer = SearchGamer.builder()
                .name(gamer.getName())
                .build();

        given(gamerSearchRepository.searchByWhere(any()))
                .willReturn(gamers);

        //when
        List<Gamer> gamerNameList = gamerService.getGamerList(searchGamer);

        //then
        assertEquals(gamerNameList.size(), 2);
        assertEquals(gamerNameList.get(0).getRace(), gamer.getRace());
    }

    @Test
    @DisplayName("게이머 닉네임 검색 조회 성공")
    void testGetGamerNickNameList(){
        //given
        List<Gamer> gamers = new ArrayList<>();
        Gamer gamer = Gamer.builder()
                .nickname("rush")
                .build();

        gamers.add(gamer);
        SearchGamer searchGamer = SearchGamer.builder()
                .nickname(gamer.getNickname())
                .build();

        given(gamerSearchRepository.searchByWhere(any()))
                .willReturn(gamers);
        //when
        List<Gamer> gamerNameList = gamerService.getGamerList(searchGamer);

        //then
        assertEquals(gamerNameList.size(), 1);
        assertEquals(gamerNameList.get(0).getNickname(), gamer.getNickname());
    }

}
