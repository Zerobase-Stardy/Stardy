package com.github.backend.persist.repository;

import com.github.backend.persist.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {

    boolean existsByTitle(String title);
}
