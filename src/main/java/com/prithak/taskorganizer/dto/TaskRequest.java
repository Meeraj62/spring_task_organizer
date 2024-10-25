package com.prithak.taskorganizer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequest {
    private String title;
    private String description;
    private LocalDateTime dueDate;
    private Long assigneeId;
}
