package com.github.backend.dto.attendance;

import com.github.backend.persist.attendance.Attendance;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import springfox.documentation.annotations.ApiIgnore;


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

		@DateTimeFormat(iso = ISO.DATE)
		private LocalDate startDate;

		@DateTimeFormat(iso = ISO.DATE)
		private LocalDate endDate;
	}


}
