package com.github.backend.service;

import com.github.backend.model.dto.AttendanceDto;
import com.github.backend.model.dto.AttendanceDto.GetRequest;
import java.util.List;

public interface AttendanceService {

	void checkTodayAttendance(String email);

	List<AttendanceDto.Info> getAttendances(GetRequest request);


}
