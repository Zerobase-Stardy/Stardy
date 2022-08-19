package com.github.backend.persist.repository;

import com.github.backend.persist.entity.Course;
import com.github.backend.persist.entity.Member;
import com.github.backend.persist.entity.MemberCourse;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberCourseRepository extends JpaRepository<MemberCourse, Long> {

	boolean existsByMemberAndCourse(Member member, Course course);

	@Query("select mc from MemberCourse mc join fetch mc.member where mc.id = :memberCourseId")
	Optional<MemberCourse> findWithMemberById(@Param("memberCourseId") Long memberCourseId);
}
