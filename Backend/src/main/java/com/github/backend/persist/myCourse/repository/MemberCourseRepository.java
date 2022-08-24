package com.github.backend.persist.myCourse.repository;

import com.github.backend.persist.course.Course;
import com.github.backend.persist.member.Member;
import com.github.backend.persist.myCourse.MyCourse;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberCourseRepository extends JpaRepository<MyCourse, Long> {

	boolean existsByMemberAndCourse(Member member, Course course);

	@Query("select mc from MyCourse mc join fetch mc.member where mc.id = :memberCourseId")
	Optional<MyCourse> findWithMemberById(@Param("memberCourseId") Long memberCourseId);
}
