package com.github.backend.service.myCourse.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import com.github.backend.persist.myCourse.MyCourse;
import com.github.backend.persist.myCourse.repository.MyCourseRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MyCourseBookmarkServiceImplTest {

	@Mock
	MyCourseRepository myCourseRepository;

	@InjectMocks
	MyCourseBookmarkServiceImpl myCourseBookmarkService;

	@DisplayName("내 강의 북마크 성공")
	@Test
	void toggleBookmark() {
		//given
		MyCourse myCourse = MyCourse.builder()
			.bookmark(false)
			.build();

		given(myCourseRepository.findByMember_IdAndCourse_Id(anyLong(), anyLong()))
			.willReturn(Optional.of(myCourse));

		//when
		boolean bookmark = myCourseBookmarkService.toggleBookmark(1L, 1L);

		//then
		assertThat(bookmark).isTrue();
	}
}