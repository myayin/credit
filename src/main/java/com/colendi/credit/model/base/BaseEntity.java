package com.colendi.credit.model.base;

import jakarta.persistence.Column;
import java.time.Instant;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@MappedSuperclass
@Accessors(chain = true)
public abstract class BaseEntity {
    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "modified_date")
    private Instant modifiedDate;

    @Column(name = "status")
    private String status;
}
