package com.prithak.taskorganizer.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BaseEntity {

    @Column(name = "created_at", updatable = false)
    @CreatedDate
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Column(name = "created_by")
    @CreatedBy
    private String createdBy;

    @Column(name = "updated_by")
    @LastModifiedBy
    private String updatedBy;
}
