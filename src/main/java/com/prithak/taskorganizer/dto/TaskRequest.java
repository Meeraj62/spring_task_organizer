package com.prithak.taskorganizer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequest {
    @NotBlank
    private String title;

    private String description;

    @NotNull
    private LocalDateTime dueDate;

    @NotNull
    private Long assigneeId;
}
