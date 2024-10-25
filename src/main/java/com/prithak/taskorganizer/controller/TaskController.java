package com.prithak.taskorganizer.controller;

import com.prithak.taskorganizer.dto.TaskRequest;
import com.prithak.taskorganizer.dto.TaskResponse;
import com.prithak.taskorganizer.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(
            @RequestBody TaskRequest request,
            Principal principal) {
        TaskResponse task = taskService.createTask(request, principal.getName());
        return ResponseEntity.ok(task);
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getUserTasks(Principal principal) {
        List<TaskResponse> tasks = taskService.getUserTasks(principal.getName());
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTask(
            @PathVariable Long id,
            Principal principal) {
        TaskResponse task = taskService.getTask(id, principal.getName());
        return ResponseEntity.ok(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable Long id,
            @RequestBody TaskRequest request,
            Principal principal) {
        TaskResponse task = taskService.updateTask(id, request, principal.getName());
        return ResponseEntity.ok(task);
    }
}
