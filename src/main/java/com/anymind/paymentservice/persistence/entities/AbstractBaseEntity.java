package com.anymind.paymentservice.persistence.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * Created by Wilson
 * On 25-12-2022, 11:02
 */
@Data
@ToString
@MappedSuperclass
@EnableJpaAuditing
public abstract class AbstractBaseEntity<T extends Serializable> {

    private static final long serialVersionUID = -5554308939380869754L;

    @Id
    @GeneratedValue
    private T id;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "modified_at", nullable = false)
    @LastModifiedDate
    private Instant modifiedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractBaseEntity<?> that = (AbstractBaseEntity<?>) o;
        return Objects.equals(id, that.id);
    }

    @PrePersist
    @PreUpdate
    public void prePersist() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }
        modifiedAt = Instant.now();
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}

