package com.prithak.taskorganizer.controller;

import com.prithak.taskorganizer.entity.Comment;
import com.prithak.taskorganizer.entity.Task;
import com.prithak.taskorganizer.entity.TaskStatus;
import com.prithak.taskorganizer.entity.User;
import com.prithak.taskorganizer.service.TaskService;
import com.prithak.taskorganizer.service.UserInfoDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private static final Logger log = LoggerFactory.getLogger(TaskController.class);
    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody Task task, @AuthenticationPrincipal UserInfoDetails user) {
        Task savedTask = taskService.createTask(task, user.getUsername());
        return new ResponseEntity<>(savedTask, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable Long id) {
        Optional<Task> task = taskService.getTaskById(id);
        return task.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<?> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody Task taskDetails) {
        Optional<Task> updatedTask = taskService.updateTask(id, taskDetails);
        return updatedTask.map(task -> new ResponseEntity<>(task, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}/transition")
    public ResponseEntity<?> transitionTaskStatus(@PathVariable Long id, @RequestBody TaskStatus status) {
        log.info("Status changed to {}", status);
        Optional<Task> updatedTask = taskService.transitionStatus(id, status);
        return updatedTask.map(task -> new ResponseEntity<>(task, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/comment")
    public ResponseEntity<?> addComment(@PathVariable Long id, @RequestBody Comment comment) {
        log.info("Adding comment to task {} with comment {}", id, comment.getContent());
        Optional<Comment> savedComment = taskService.addCommentToTask(id, comment);
        return savedComment.map(value -> new ResponseEntity<>(value, HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        boolean isDeleted = taskService.deleteTask(id);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
