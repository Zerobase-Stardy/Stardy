package com.github.backend.service.myCourse.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.github.backend.dto.myCourse.MyCourseSearchDto.Info;
import com.github.backend.dto.myCourse.MyCourseSearchDto.Request;
import com.github.backend.persist.myCourse.repository.querydsl.MyCourseSearchRepository;
import java.util.ArrayList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class MyCourseInfoSearchServiceImplTest {

	@Mock
	MyCourseSearchRepository myCourseSearchRepository;

	@InjectMocks
	MyCourseInfoSearchServiceImpl myCourseInfoSearchService;

	@DisplayName("내 강의 검색 성공")
	@Test
	void searchMyCourses() {
		//given
		ArrayList<Info> infos = new ArrayList<>();

		for (long i = 0; i < 3; i++) {
			Info info = Info.builder()
				.courseId(i)
				.build();

			infos.add(info);
		}

		PageImpl<Info> expectedPage = new PageImpl<>(infos);

		Request request = Request.builder()
			.build();

		given(myCourseSearchRepository.search(any(),any()))
			.willReturn(expectedPage);

		//when
		Page<Info> receivedPage =
			myCourseInfoSearchService.searchMyCourses(PageRequest.of(0,10),request);

		//then
		assertThat(receivedPage.getSize()).isEqualTo(expectedPage.getSize());
	}
}