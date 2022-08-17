package com.github.backend.persist.repository;

import com.github.backend.persist.entity.Attendance;
import com.github.backend.persist.entity.Member;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

	boolean existsByMemberAndAttendanceDate(Member member,LocalDate date);

}
