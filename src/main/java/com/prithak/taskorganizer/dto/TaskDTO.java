package com.prithak.taskorganizer.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TaskDTO {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    private LocalDateTime dueDate;

    private String assigneeEmail;

    private List<MultipartFile> attachments;
}
