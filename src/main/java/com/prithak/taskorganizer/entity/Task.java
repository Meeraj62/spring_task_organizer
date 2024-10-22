package com.prithak.taskorganizer.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Table(name = "tasks")
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User assignee;

    private String title;

    private String description;

    private String dueDate;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private List<TaskAttachment> attachments = new ArrayList<>();

    @CreatedDate
    private LocalDateTime created_at;

    @LastModifiedDate
    private LocalDateTime updated_at;

    @CreatedBy
    private String created_by;

    @LastModifiedBy
    private String updated_by;
}
