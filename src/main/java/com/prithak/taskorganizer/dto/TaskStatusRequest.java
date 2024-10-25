package com.prithak.taskorganizer.dto;

import com.prithak.taskorganizer.entity.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskStatusRequest {
    private TaskStatus status;
}
