package com.github.backend.service.attendance;

import com.github.backend.dto.attendance.AttendanceDto;
import com.github.backend.dto.attendance.AttendanceDto.GetRequest;
import java.util.List;

public interface AttendanceService {

	void checkTodayAttendance(String email);

	List<AttendanceDto.Info> getAttendances(GetRequest request);

}
