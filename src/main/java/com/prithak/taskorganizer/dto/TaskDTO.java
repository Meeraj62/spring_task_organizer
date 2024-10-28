package com.prithak.taskorganizer.dto;

import com.prithak.taskorganizer.entity.TaskStatus;
import com.prithak.taskorganizer.entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class TaskDTO {
    private Long id;
    @NotBlank(message = "Title is required")
    private String title;
    private String description;
    private LocalDate dueDate;
    private TaskStatus status;
    private User assignee;
    private String attachments;
    private List<CommentDTO> comments;
}
