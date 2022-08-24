package com.github.backend.dto.myCourse;

import com.github.backend.persist.myCourse.repository.querydsl.cond.MyCourseSearchCond;
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

		private Long memberId;
		private String title;
		private String gamerNickname;
		private String gamerName;
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
