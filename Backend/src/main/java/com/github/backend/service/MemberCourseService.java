package com.github.backend.service;

public interface MemberCourseService {

	void takeCourse(Long memberId, Long courseId);

	void toggleBookmark(String email, Long memberCourseId);
}
