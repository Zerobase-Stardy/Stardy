package com.github.backend.dto.attendance;

import com.github.backend.persist.attendance.Attendance;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;


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

		@DateTimeFormat(iso = ISO.DATE)
		private LocalDate startDate;
		@DateTimeFormat(iso = ISO.DATE)
		private LocalDate endDate;
	}


}
