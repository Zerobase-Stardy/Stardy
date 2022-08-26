package com.github.backend.dto.attendance;

import com.github.backend.persist.attendance.Attendance;
import io.swagger.annotations.ApiParam;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


public class AttendanceDto {

	@Builder
	@Data
	public static class Info{
		private Long memberId;
		private LocalDate date;

		public static Info of(Attendance attendance) {
			return Info.builder()
				.memberId(attendance.getMember().getId())
				.date(attendance.getAttendanceDate())
				.build();
		}
	}


	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	@Data
	public static class GetRequest{
		@ApiParam(hidden = true)
		private Long memberId;

		@ApiParam(value = "시작 날짜",defaultValue = "2022-08-01")
		private LocalDate startDate;
		@ApiParam(value = "종료 날짜",example = "2022-08-31")
		private LocalDate endDate;
	}


}
