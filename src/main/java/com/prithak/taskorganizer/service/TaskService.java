package com.prithak.taskorganizer.service;

import com.prithak.taskorganizer.entity.Comment;
import com.prithak.taskorganizer.entity.Task;
import com.prithak.taskorganizer.entity.TaskStatus;
import com.prithak.taskorganizer.entity.User;
import com.prithak.taskorganizer.repository.CommentRepository;
import com.prithak.taskorganizer.repository.TaskRepository;
import com.prithak.taskorganizer.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private static final Logger log = LoggerFactory.getLogger(TaskService.class);
    private final TaskRepository taskRepository;

    private final CommentRepository commentRepository;

    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, CommentRepository commentRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    public Task createTask(Task task, String assigneeUsername) {
        Optional<User> assignee = userRepository.findByUsername(assigneeUsername);

        if (assignee.isPresent()) {
            task.setAssignee(assignee.get());
            task.setStatus(TaskStatus.OPEN);
            return taskRepository.save(task);
        } else {
            throw new IllegalArgumentException("Assignee not found");
        }
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> updateTask(Long id, Task taskDetails) {
        return taskRepository.findById(id).map(task -> {
            task.setTitle(taskDetails.getTitle());
            task.setDescription(taskDetails.getDescription());
            task.setDueDate(taskDetails.getDueDate());
            task.setAssignee(taskDetails.getAssignee());
            return taskRepository.save(task);
        });
    }

    public Optional<Task> transitionStatus(Long id, TaskStatus status) {
        return taskRepository.findById(id).map(task -> {
            task.setStatus(status);
            return taskRepository.save(task);
        });
    }

    public Optional<Comment> addCommentToTask(Long taskId, Comment comment) {
        return taskRepository.findById(taskId).map(task -> {
            comment.setTask(task);
            return commentRepository.save(comment);
        });
    }

    public boolean deleteTask(Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
