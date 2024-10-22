package com.prithak.taskorganizer.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "task_attachments")
public class TaskAttachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    private String fileType;

    private String filePath;

    @ManyToOne
    private Task task;
}
