package com.prithak.taskorganizer.service;

import com.prithak.taskorganizer.dto.CommentDTO;
import com.prithak.taskorganizer.dto.TaskDTO;
import com.prithak.taskorganizer.entity.Comment;
import com.prithak.taskorganizer.entity.Task;
import com.prithak.taskorganizer.entity.TaskStatus;
import com.prithak.taskorganizer.entity.User;
import com.prithak.taskorganizer.repository.CommentRepository;
import com.prithak.taskorganizer.repository.TaskRepository;
import com.prithak.taskorganizer.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public TaskDTO createTask(TaskDTO taskDTO, String assigneeUsername) {
        Optional<User> assignee = userRepository.findByUsername(assigneeUsername);

        if (assignee.isPresent()) {
            Task task = mapToTask(taskDTO);
            task.setAssignee(assignee.get());
            task.setStatus(TaskStatus.OPEN);
            taskRepository.save(task);
            return taskDTO;
        } else {
            throw new IllegalArgumentException("Assignee not found");
        }
    }

    private TaskDTO mapToTaskDTO(Task task) {
        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setDueDate(task.getDueDate());
        dto.setStatus(task.getStatus());
        dto.setAssignee(task.getAssignee() != null ? task.getAssignee() : null);
        dto.setAttachments(task.getAttachments());
//        dto.setComments(task.getComments().stream().map(Comment::getContent).collect(Collectors.toList()));
        return dto;
    }

    private Task mapToTask(TaskDTO dto) {
        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setDueDate(dto.getDueDate());
        task.setAttachments(dto.getAttachments());
        return task;
    }

    public Optional<TaskDTO> getTaskById(Long id) {
        Optional<Task> task = taskRepository.findById(id);
        return task.map(this::mapToTaskDTO);
    }

    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll().stream().map(this::mapToTaskDTO).collect(Collectors.toList());
    }

    public Optional<TaskDTO> updateTask(Long id, TaskDTO taskDetails) {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isPresent()) {
            mapToTask(taskDetails);
            taskRepository.save(task.get());
        }
        return task.map(this::mapToTaskDTO);
    }

    public Optional<TaskDTO> transitionStatus(Long id, TaskStatus status) {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isPresent()) {
            task.get().setStatus(status);
            taskRepository.save(task.get());
        }
        return task.map(this::mapToTaskDTO);
    }

    public Optional<CommentDTO> addCommentToTask(Long taskId, CommentDTO commentDTO, String username) {
        Optional<Task> task = taskRepository.findById(taskId);
        if (task.isPresent()) {
            Comment comment = new Comment();
            comment.setTask(task.get());
            comment.setContent(commentDTO.getContent());
//            comment.setCreatedBy(username);
            commentRepository.save(comment);
        }
        return Optional.ofNullable(commentDTO);
    }

    public void deleteTask(Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
        }
    }
}
