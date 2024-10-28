package com.prithak.taskorganizer.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDTO {
    private Long id;
    private String content;
    private String author;
    private String createdBy;
    private LocalDateTime createdAt;
}
