package com.assignment.spring.entity;

import lombok.Getter;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public abstract class AbstractAuditable {

    @Type(type = "org.hibernate.type.LocalDateTimeType")
    @Column(updatable = false)
    @CreatedDate
    protected LocalDateTime createdAt;
}
