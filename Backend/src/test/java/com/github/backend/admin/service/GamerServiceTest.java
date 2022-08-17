package com.github.backend.admin.service;

import com.github.backend.exception.AdminException;
import com.github.backend.exception.GamerException;
import com.github.backend.model.constants.Role;
import com.github.backend.persist.entity.Gamer;
import com.github.backend.persist.repository.GamerRepository;
import com.github.backend.service.GamerService;
import com.github.backend.type.AdminErrorCode;
import com.github.backend.type.GamerErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class GamerServiceTest {

    @Mock
    private GamerRepository gamerRepository;

    @InjectMocks
    private GamerService gamerService;

    @Test
    @DisplayName("게이머 등록 성공")
    void testRegisterGamer(){
        //given
        given(gamerRepository.save(any()))
                .willReturn(
                        Gamer.builder()
                                .name("유영진")
                                .race("테란")
                                .nickName("rush")
                                .introduce("단단한 테란")
                                .build()
                );
        //when
        
        Gamer gamer = gamerService.registerGamer(
                "유영진","테란","rush","단단한 테란"
        );
        //then
        assertEquals(gamer.getName(), "유영진");
        assertEquals(gamer.getRace(), "테란");
        assertEquals(gamer.getNickName(), "rush");
        assertEquals(gamer.getIntroduce(), "단단한 테란");
    }

    @Test
    @DisplayName("게이머 등록 실패 - 중복 사용자")
    void testRegisterGamerFailed(){
        //given
        given(gamerRepository.existsByNickName(anyString()))
                .willReturn(true);
        //when
        GamerException gamerException = assertThrows(GamerException.class,
                () -> gamerService.registerGamer("유영진","테란","rush","단단한 테란"));

        //then
        assertEquals(gamerException.getErrorCode(), GamerErrorCode.EXIST_SAME_GAMER_NICKNAME);
    }

}
