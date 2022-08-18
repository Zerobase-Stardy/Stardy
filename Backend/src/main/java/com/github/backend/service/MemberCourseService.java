package com.github.backend.service;

public interface MemberCourseService {

	void takeCourse(String email, Long courseId);

	void toggleBookmark(String email, Long memberCourseId);
}
