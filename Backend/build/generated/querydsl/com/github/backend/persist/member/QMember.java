package com.github.backend.persist.member;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = 1489861020L;

    public static final QMember member = new QMember("member1");

    public final com.github.backend.persist.common.QBaseTimeEntity _super = new com.github.backend.persist.common.QBaseTimeEntity(this);

    public final EnumPath<com.github.backend.persist.common.type.AuthType> authType = createEnum("authType", com.github.backend.persist.common.type.AuthType.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath nickname = createString("nickname");

    public final NumberPath<Long> point = createNumber("point", Long.class);

    public final EnumPath<com.github.backend.persist.member.type.Role> role = createEnum("role", com.github.backend.persist.member.type.Role.class);

    public final EnumPath<com.github.backend.persist.member.type.MemberStatus> status = createEnum("status", com.github.backend.persist.member.type.MemberStatus.class);

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

