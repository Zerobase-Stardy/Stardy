package com.github.backend.dto.myCourse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class BookMarkToggleDto {

	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	@Data
	public static class Info {

		private boolean bookmark;

	}

}
