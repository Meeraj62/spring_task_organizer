package com.prithak.taskorganizer.service;

import com.prithak.taskorganizer.dto.TaskRequest;
import com.prithak.taskorganizer.dto.TaskResponse;
import com.prithak.taskorganizer.entity.Task;
import com.prithak.taskorganizer.entity.User;
import com.prithak.taskorganizer.repository.TaskRepository;
import com.prithak.taskorganizer.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }


    public TaskResponse createTask(TaskRequest request, String creatorEmail) {
        User creator = userRepository.findByEmail(creatorEmail)
                .orElseThrow(() -> new RuntimeException("Creator not found"));

        User assignee = userRepository.findById(request.getAssigneeId())
                .orElseThrow(() -> new RuntimeException("Assignee not found"));

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setDueDate(request.getDueDate());
        task.setCreatedBy(creator);
        task.setAssignee(assignee);

        Task savedTask = taskRepository.save(task);
        return convertToResponse(savedTask);
    }

    public List<TaskResponse> getUserTasks(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return taskRepository.findByCreatedByIdOrAssigneeId(user.getId(), user.getId())
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public TaskResponse getTask(Long id, String userEmail) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!task.getCreatedBy().getId().equals(user.getId()) &&
                !task.getAssignee().getId().equals(user.getId())) {
            throw new RuntimeException("Access denied");
        }

        return convertToResponse(task);
    }

    public TaskResponse updateTask(Long id, TaskRequest request, String userEmail) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!task.getCreatedBy().getId().equals(user.getId())) {
            throw new RuntimeException("Only creator can update task");
        }

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setDueDate(request.getDueDate());

        if (request.getAssigneeId() != null) {
            User newAssignee = userRepository.findById(request.getAssigneeId())
                    .orElseThrow(() -> new RuntimeException("Assignee not found"));
            task.setAssignee(newAssignee);
        }

        Task updatedTask = taskRepository.save(task);
        return convertToResponse(updatedTask);
    }

    private TaskResponse convertToResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                task.getStatus(),
                task.getCreatedBy().getEmail(),
                task.getAssignee().getEmail(),
                task.getCreatedAt()
        );
    }
}
