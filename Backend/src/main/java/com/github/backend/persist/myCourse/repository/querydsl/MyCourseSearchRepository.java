package com.github.backend.persist.myCourse.repository.querydsl;

import static com.github.backend.persist.course.QCourse.course;
import static com.github.backend.persist.gamer.QGamer.gamer;
import static com.github.backend.persist.myCourse.QMyCourse.myCourse;
import static org.springframework.util.StringUtils.hasText;

import com.github.backend.dto.myCourse.MyCourseSearchDto.Info;
import com.github.backend.persist.myCourse.repository.querydsl.cond.MyCourseSearchCond;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

@RequiredArgsConstructor
@Repository
public class MyCourseSearchRepository {

	private final JPAQueryFactory queryFactory;

	public Page<Info> search(Pageable pageable, MyCourseSearchCond cond) {
		// 페이징 쿼리 작성
		List<Info> content = queryFactory
			.select(Projections.fields(Info.class,
				course.id.as("courseId"),
				course.thumbnailUrl.as("thumbnail"),
				course.title
			))
			.from(myCourse)
			.leftJoin(myCourse.course, course)
			.leftJoin(course.gamer, gamer)
			.where(myCourse.member.id.eq(cond.getMemberId()),
				gamerNicknameLike(cond.getGamerNickname()),
				titleLike(cond.getTitle()),
				gamerNameLike(cond.getGamerName()),
				bookmarkEq(cond.isBookmark())
			)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		// count 쿼리 작성
		JPAQuery<Long> countQuery = queryFactory
			.select(myCourse.count())
			.from(myCourse)
			.leftJoin(myCourse.course, course)
			.leftJoin(course.gamer, gamer)
			.where(memberIdEq(cond.getMemberId()),
				gamerNicknameLike(cond.getGamerNickname()),
				titleLike(cond.getTitle()),
				gamerNameLike(cond.getGamerName()),
				bookmarkEq(cond.isBookmark())
			);

		// Page 반환 - count query 최적화
		return PageableExecutionUtils.getPage(content, pageable,
			countQuery::fetchOne);
	}

	private BooleanExpression titleLike(String title) {
		return hasText(title) ? course.title.contains(title) : null;
	}

	private BooleanExpression memberIdEq(Long memberId) {
		return !ObjectUtils.isEmpty(memberId) ? myCourse.member.id.eq(memberId) : null;
	}

	private BooleanExpression gamerNicknameLike(String gamerNickname) {
		return hasText(gamerNickname) ? gamer.nickname.contains(gamerNickname) : null;
	}

	private BooleanExpression gamerNameLike(String gamerName) {
		return hasText(gamerName) ? gamer.name.contains(gamerName) : null;
	}

	private BooleanExpression bookmarkEq(boolean bookmark) {
		return bookmark ? myCourse.bookmark.eq(true) : null;
	}
}
