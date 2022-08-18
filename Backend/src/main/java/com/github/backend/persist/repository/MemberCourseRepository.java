package com.github.backend.persist.repository;

import com.github.backend.persist.entity.Course;
import com.github.backend.persist.entity.Member;
import com.github.backend.persist.entity.MemberCourse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCourseRepository extends JpaRepository<MemberCourse, Long> {

	boolean existsByMemberAndCourse(Member member, Course course);
}
