package com.prithak.taskorganizer.controller;

import com.prithak.taskorganizer.service.TaskService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private TaskService taskService;
}
