package com.github.backend.model.dto;

import com.github.backend.persist.entity.Attendance;
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
		private Long memberId;
		private LocalDate startDate;
		private LocalDate endDate;
	}


}
