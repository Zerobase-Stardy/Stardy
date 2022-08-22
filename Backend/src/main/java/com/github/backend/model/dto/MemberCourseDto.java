package com.github.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class MemberCourseDto {

	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	@Data
	public static class TakeRequest {
		private Long courseId;
	}


}
