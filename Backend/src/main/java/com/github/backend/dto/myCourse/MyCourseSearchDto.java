package com.github.backend.dto.myCourse;

import com.github.backend.persist.myCourse.repository.querydsl.cond.MyCourseSearchCond;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class MyCourseSearchDto {

	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	@Data
	public static class Request {

		@ApiParam(hidden = true)
		private Long memberId;

		@ApiParam(value = "강의 제목")
		private String title;

		@ApiParam(value = "게이머 닉네임")
		private String gamerNickname;
		@ApiParam(value = "게이머 이름")
		private String gamerName;

		@ApiParam(value = "즐겨찾기 여부")
		private boolean bookmark;

		public MyCourseSearchCond toCond() {
			return MyCourseSearchCond.builder()
				.memberId(this.memberId)
				.title(this.title)
				.gamerNickname(this.gamerNickname)
				.gamerName(this.gamerName)
				.bookmark(this.bookmark)
				.build();
		}
	}

	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	@Data
	public static class Info {

		private Long courseId;
		private String thumbnail;
		private String title;
	}


}
