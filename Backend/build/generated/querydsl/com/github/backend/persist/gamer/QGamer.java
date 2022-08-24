package com.github.backend.persist.gamer;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QGamer is a Querydsl query type for Gamer
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGamer extends EntityPathBase<Gamer> {

    private static final long serialVersionUID = -1408997760L;

    public static final QGamer gamer = new QGamer("gamer");

    public final com.github.backend.persist.common.QBaseTimeEntity _super = new com.github.backend.persist.common.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath introduce = createString("introduce");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath name = createString("name");

    public final StringPath nickname = createString("nickname");

    public final StringPath race = createString("race");

    public QGamer(String variable) {
        super(Gamer.class, forVariable(variable));
    }

    public QGamer(Path<? extends Gamer> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGamer(PathMetadata metadata) {
        super(Gamer.class, metadata);
    }

}

