package com.prithak.taskorganizer.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    private TaskStatus status = TaskStatus.OPEN;

    @ManyToOne
    private User assignee;

    private String attachments;
}
