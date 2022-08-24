package com.github.backend.persist.course.repository;

import com.github.backend.persist.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {

    boolean existsByTitle(String title);
}
