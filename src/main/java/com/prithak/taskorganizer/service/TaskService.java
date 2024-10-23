package com.prithak.taskorganizer.service;

import com.prithak.taskorganizer.dto.TaskDTO;
import com.prithak.taskorganizer.entity.Task;
import com.prithak.taskorganizer.entity.User;
import com.prithak.taskorganizer.repository.TaskRepository;
import com.prithak.taskorganizer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    public TaskDTO createTask(TaskDTO taskDTO) {
        User assignee = userRepository.findByEmail(taskDTO.getAssigneeEmail()).orElseThrow(() -> new RuntimeException("User not found"));

        Task newTask = new Task();
        newTask.setAssignee(assignee);
        newTask.setDescription(taskDTO.getDescription());
        newTask.setTitle(taskDTO.getTitle());
        newTask.setDueDate(taskDTO.getDueDate());

        taskRepository.save(newTask);
        return taskDTO;
    }
}
