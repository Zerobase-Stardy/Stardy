package com.github.backend.persist.myCourse.repository.querydsl.cond;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MyCourseSearchCond {
	private Long memberId;
	private String title;
	private String gamerNickname;
	private String gamerName;
	private boolean bookmark;

}
