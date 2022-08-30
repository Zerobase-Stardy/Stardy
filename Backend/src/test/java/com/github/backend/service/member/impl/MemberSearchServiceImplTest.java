package com.github.backend.service.member.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import com.github.backend.dto.member.SearchMember;
import com.github.backend.persist.member.Member;
import com.github.backend.persist.member.repository.MemberRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MemberSearchServiceImplTest {

	@Mock
	MemberRepository memberRepository;

	@InjectMocks
	MemberSearchServiceImpl memberInfoSearchService;


	@DisplayName("내 정보 조회 성공")
	@Test
	void searchMember_success() {
		//given
		Member member = Member.builder()
			.id(1L)
			.email("test@test.com")
			.nickname("test")
			.point(500L)
			.build();

		given(memberRepository.findById(anyLong()))
			.willReturn(Optional.of(member));

		//when
		SearchMember searchMember = memberInfoSearchService.searchMember(member.getId());

		//then
		assertThat(searchMember.getEmail()).isEqualTo(member.getEmail());
		assertThat(searchMember.getNickname()).isEqualTo(member.getNickname());
		assertThat(searchMember.getPoint()).isEqualTo(member.getPoint());
	}
}