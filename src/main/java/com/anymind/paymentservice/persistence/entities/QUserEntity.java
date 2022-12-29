package com.anymind.paymentservice.persistence.entities;

import com.anymind.paymentservice.web.models.enums.UserRole;
import com.querydsl.core.annotations.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.*;

import java.io.Serial;
import java.time.Instant;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

/**
 * Created by Wilson
 * On 01-01-2023, 12:26
 */

@Generated("com.mysema.query.codegen.EntitySerializer")
public class QUserEntity extends EntityPathBase<UserEntity> {

    @Serial
    private static final long serialVersionUID = -797939782L;

    public static final QUserEntity userEntity = new QUserEntity("userEntity");

    public final NumberPath<Long> id = createNumber("id", Long.class);
    public final DateTimePath<Instant> createdAt = createDateTime("createdAt", java.time.Instant.class);
    public final DateTimePath<Instant> modifiedAt = createDateTime("modifiedAt", java.time.Instant.class);

    public final StringPath customerId = createString("customerId");
    public final StringPath firstName = createString("firstName");
    public final StringPath lastName = createString("lastName");
    public final StringPath email = createString("email");
    public final EnumPath<UserRole> role = createEnum("role", UserRole.class);

    public QUserEntity(String variable) {
        super(UserEntity.class, forVariable(variable));
    }

    public QUserEntity(Path<UserEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserEntity(PathMetadata metadata) {
        super(UserEntity.class, metadata);
    }

}
