package com.prithak.taskorganizer.entity;

import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "task_comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Task task;

    private String content;
}
