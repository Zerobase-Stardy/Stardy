package com.github.backend.persist.attendance.repository;

import com.github.backend.persist.attendance.Attendance;
import com.github.backend.persist.member.Member;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

	boolean existsByMemberAndAttendanceDate(Member member,LocalDate date);
	@Query("select a from Attendance a where a.member.id = :memberId and a.attendanceDate between :startDate and :endDate order by a.attendanceDate asc")
	List<Attendance> findAllByMember_Id(@Param("memberId") Long id, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}
