package com.github.backend.service.admin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.github.backend.dto.gamer.GamerInfoOutputDto;
import com.github.backend.dto.gamer.SearchGamer;
import com.github.backend.persist.gamer.Gamer;
import com.github.backend.persist.gamer.repository.GamerRepository;
import com.github.backend.persist.gamer.repository.querydsl.GamerSearchRepository;
import com.github.backend.service.gamer.impl.GamerService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

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
            gamer.setId((long) (i + 1));
            gamers.add(gamer);
        }

        SearchGamer searchGamer = SearchGamer.builder()
                .name(gamer.getName())
                .race(gamer.getRace())
                .nickname(gamer.getNickname())
                .build();

        given(gamerSearchRepository.searchByWhere(any(), any()))
                .willReturn(new PageImpl<>(gamers));

        Pageable pageable = PageRequest.of(1, 2, Sort.Direction.DESC, "id");

        //when
        Page<GamerInfoOutputDto.Info> gamerNicknameList = gamerService.getGamerList(searchGamer, pageable);

        //then
        assertEquals(gamerNicknameList.getContent().size(), gamers.size());
        assertEquals(gamerNicknameList.getContent().get(0).getId(), gamers.get(0).getId());
        assertEquals(gamerNicknameList.getContent().get(1).getId(), gamers.get(1).getId());
    }

    @Test
    @DisplayName("게이머 이름 검색 조회 성공")
    void testGetGamerNameList(){
        //given
        Gamer gamer = Gamer.builder()
                .name("유영진")
                .race("테란")
                .nickname("rush")
                .introduce("단단한 테란")
                .build();



        List<Gamer> gamers = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            gamer.setId((long) (i + 1));
            gamers.add(gamer);
        }

        SearchGamer searchGamer = SearchGamer.builder()
                .name(gamer.getName())
                .race(gamer.getRace())
                .nickname(gamer.getNickname())
                .build();

        given(gamerSearchRepository.searchByWhere(any(), any()))
                .willReturn(new PageImpl<>(gamers));

        Pageable pageable = PageRequest.of(1, 2, Sort.Direction.DESC, "id");

        //when
        Page<GamerInfoOutputDto.Info> gamerNameList = gamerService.getGamerList(searchGamer, pageable);

        //then
        assertEquals(gamerNameList.getContent().size(), gamers.size());
        assertEquals(gamerNameList.getContent().get(0).getName(), gamer.getName());
    }

    @Test
    @DisplayName("게이머 종족 검색 조회 성공")
    void testGetGamerRaceList(){
        //given
        Gamer gamer = Gamer.builder()
                .name("유영진")
                .race("테란")
                .nickname("rush")
                .introduce("단단한 테란")
                .build();

        List<Gamer> gamers = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            gamer.setId((long) (i + 1));
            gamers.add(gamer);
        }

        SearchGamer searchGamer = SearchGamer.builder()
                .name(gamer.getName())
                .race(gamer.getRace())
                .nickname(gamer.getNickname())
                .build();

        given(gamerSearchRepository.searchByWhere(any(), any()))
                .willReturn(new PageImpl<>(gamers));

        Pageable pageable = PageRequest.of(1, 2, Sort.Direction.DESC, "id");

        //when
        Page<GamerInfoOutputDto.Info> gamerRaceList = gamerService.getGamerList(searchGamer, pageable);

        //then
        assertEquals(gamerRaceList.getContent().size(), gamers.size());
        assertEquals(gamerRaceList.getContent().get(0).getRace(), gamer.getRace());
    }

    @Test
    @DisplayName("게이머 닉네임 검색 조회 성공")
    void testGetGamerNickNameList(){
        //given
        Gamer gamer = Gamer.builder()
                .name("유영진")
                .race("테란")
                .nickname("rush")
                .introduce("단단한 테란")
                .build();



        List<Gamer> gamers = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            gamer.setId((long) (i + 1));
            gamers.add(gamer);
        }

        SearchGamer searchGamer = SearchGamer.builder()
                .name(gamer.getName())
                .race(gamer.getRace())
                .nickname(gamer.getNickname())
                .build();

        given(gamerSearchRepository.searchByWhere(any(), any()))
                .willReturn(new PageImpl<>(gamers));

        Pageable pageable = PageRequest.of(1, 2, Sort.Direction.DESC, "id");

        //when
        Page<GamerInfoOutputDto.Info> gamerNicknameList = gamerService.getGamerList(searchGamer, pageable);

        //then
        assertEquals(gamerNicknameList.getContent().size(), gamers.size());
        assertEquals(gamerNicknameList.getContent().get(0).getNickname(), gamer.getNickname());
    }

}
