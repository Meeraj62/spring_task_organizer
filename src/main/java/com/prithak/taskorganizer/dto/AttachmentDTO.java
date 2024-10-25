package com.prithak.taskorganizer.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AttachmentDTO {
    private Long id;
    private String fileName;
    private String fileType;
    private LocalDateTime createdAt;
    private String createdBy;
}
