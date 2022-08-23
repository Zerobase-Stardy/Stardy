package com.github.backend.persist.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberCourse is a Querydsl query type for MemberCourse
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberCourse extends EntityPathBase<MemberCourse> {

    private static final long serialVersionUID = -1637980626L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberCourse memberCourse = new QMemberCourse("memberCourse");

    public final BooleanPath bookmark = createBoolean("bookmark");

    public final QCourse course;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember member;

    public QMemberCourse(String variable) {
        this(MemberCourse.class, forVariable(variable), INITS);
    }

    public QMemberCourse(Path<? extends MemberCourse> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberCourse(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberCourse(PathMetadata metadata, PathInits inits) {
        this(MemberCourse.class, metadata, inits);
    }

    public QMemberCourse(Class<? extends MemberCourse> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.course = inits.isInitialized("course") ? new QCourse(forProperty("course"), inits.get("course")) : null;
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

