package com.prithak.taskorganizer.controller;

import com.prithak.taskorganizer.dto.CommentRequest;
import com.prithak.taskorganizer.dto.TaskRequest;
import com.prithak.taskorganizer.dto.TaskResponse;
import com.prithak.taskorganizer.entity.TaskStatus;
import com.prithak.taskorganizer.service.TaskService;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(
            @Valid @RequestBody TaskRequest request,
            Authentication authentication
    ) {
        return ResponseEntity.ok(taskService.createTask(request, authentication.getName()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTask(@PathVariable Long id, Authentication authentication) {
        return ResponseEntity.ok(taskService.getTask(id, authentication.getName()));
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getTasks(Authentication authentication) {
        return ResponseEntity.ok(taskService.getTasks(authentication.getName()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskRequest request,
            Authentication authentication
    ) {
        return ResponseEntity.ok(taskService.updateTask(id, request, authentication.getName()));
    }

    @PutMapping("/{id}/transition")
    public ResponseEntity<TaskResponse> transitionTaskStatus(
            @PathVariable Long id,
            @RequestParam TaskStatus status,
            Authentication authentication
    ) {
        return ResponseEntity.ok(taskService.transitionTaskStatus(id, status, authentication.getName()));
    }

    @PutMapping("/{id}/comment")
    public ResponseEntity<TaskResponse> addComment(
            @PathVariable Long id,
            @Valid @RequestBody CommentRequest request,
            Authentication authentication
    ) {
        return ResponseEntity.ok(taskService.addComment(id, request, authentication.getName()));
    }

    @PostMapping("/{id}/attachments")
    public ResponseEntity<TaskResponse> addAttachment(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file,
            Authentication authentication
    ) {
        return ResponseEntity.ok(taskService.addAttachment(id, file, authentication.getName()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id, Authentication authentication) {
        taskService.deleteTask(id, authentication.getName());
        return ResponseEntity.noContent().build();
    }
}
