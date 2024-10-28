package com.prithak.taskorganizer.controller;

import com.prithak.taskorganizer.dto.CommentDTO;
import com.prithak.taskorganizer.dto.TaskDTO;
import com.prithak.taskorganizer.entity.TaskStatus;
import com.prithak.taskorganizer.exceptions.ResourceNotFoundException;
import com.prithak.taskorganizer.service.TaskService;
import com.prithak.taskorganizer.service.UserInfoDetails;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private static final Logger log = LoggerFactory.getLogger(TaskController.class);

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@Valid @RequestBody TaskDTO taskDTO, @AuthenticationPrincipal UserInfoDetails user) {
        log.debug("Creating new task for user: {}", user.getUsername());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(taskService.createTask(taskDTO, user.getUsername()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
    }

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        List<TaskDTO> tasks = taskService.getAllTasks();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskDTO taskDetails) {
        return ResponseEntity.ok(taskService.updateTask(id, taskDetails)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id)));
    }

    @PutMapping("/{id}/transition")
    public ResponseEntity<TaskDTO> updateTaskStatus(
            @PathVariable Long id,
            @Valid @RequestBody TaskStatus status) {
        log.info("Updating status for task id: {} to: {}", id, status);
        return ResponseEntity.ok(taskService.transitionStatus(id, status)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id)));
    }

    @PostMapping("/{id}/comment")
    public ResponseEntity<CommentDTO> addComment(
            @PathVariable Long id,
            @Valid @RequestBody CommentDTO comment,
            @AuthenticationPrincipal UserInfoDetails user) {
        log.debug("Adding comment to task: {}", id);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(taskService.addCommentToTask(id, comment, user.getUsername())
                        .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
