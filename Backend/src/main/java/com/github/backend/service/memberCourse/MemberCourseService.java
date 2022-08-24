package com.github.backend.service.memberCourse;

public interface MemberCourseService {

	void takeCourse(String email, Long courseId);

	void toggleBookmark(String email, Long memberCourseId);
}
