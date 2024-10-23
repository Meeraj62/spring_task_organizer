package com.prithak.taskorganizer.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class TaskDTO {

    private String title;

    private String description;

    private String dueDate;

    private String assigneeEmail;

    private List<MultipartFile> attachments;
}
