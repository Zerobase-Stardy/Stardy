package com.github.backend.persist.myCourse.repository;

import com.github.backend.persist.course.Course;
import com.github.backend.persist.member.Member;
import com.github.backend.persist.myCourse.MyCourse;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyCourseRepository extends JpaRepository<MyCourse, Long> {

	boolean existsByMemberAndCourse(Member member, Course course);

	Optional<MyCourse> findByMember_IdAndCourse_Id(Long memberId, Long courseId);
}
