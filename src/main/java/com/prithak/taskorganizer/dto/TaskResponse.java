package com.prithak.taskorganizer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime dueDate;
    private String status;
    private String creatorEmail;
    private String assigneeEmail;
    private LocalDateTime createdAt;
}
