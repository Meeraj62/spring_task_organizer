package com.prithak.taskorganizer.service;

import com.prithak.taskorganizer.dto.*;
import com.prithak.taskorganizer.entity.*;
import com.prithak.taskorganizer.repository.TaskRepository;
import com.prithak.taskorganizer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Transactional
    public TaskResponse createTask(TaskRequest request, String creatorEmail) {
        var creator = getUserByEmail(creatorEmail);
        var assignee = userRepository.findById(request.getAssigneeId())
                .orElseThrow(() -> new IllegalArgumentException("Assignee not found"));

        var task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .dueDate(request.getDueDate())
                .assignee(assignee)
                .status(TaskStatus.OPEN)
                .build();

        return mapToTaskResponse(taskRepository.save(task));
    }

    @Transactional(readOnly = true)
    public TaskResponse getTask(Long id, String userEmail) {
        var task = getTaskById(id);
        validateTaskAccess(task, userEmail);
        return mapToTaskResponse(task);
    }

    @Transactional(readOnly = true)
    public List<TaskResponse> getTasks(String userEmail) {
        var user = getUserByEmail(userEmail);
        return taskRepository.findByCreatedByOrAssignee(userEmail, user)
                .stream()
                .map(this::mapToTaskResponse)
                .toList();
    }

    @Transactional
    public TaskResponse updateTask(Long id, TaskRequest request, String userEmail) {
        var task = getTaskById(id);
        validateTaskAccess(task, userEmail);

        var assignee = userRepository.findById(request.getAssigneeId())
                .orElseThrow(() -> new IllegalArgumentException("Assignee not found"));

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setDueDate(request.getDueDate());
        task.setAssignee(assignee);

        return mapToTaskResponse(taskRepository.save(task));
    }

    @Transactional
    public TaskResponse transitionTaskStatus(Long id, TaskStatus status, String userEmail) {
        var task = getTaskById(id);
        validateTaskAccess(task, userEmail);
        task.setStatus(status);
        return mapToTaskResponse(taskRepository.save(task));
    }

    @Transactional
    public TaskResponse addComment(Long id, CommentRequest request, String userEmail) {
        var task = getTaskById(id);
        validateTaskAccess(task, userEmail);

        var comment = TaskComment.builder()
                .task(task)
                .content(request.getContent())
                .build();

        task.getComments().add(comment);
        return mapToTaskResponse(taskRepository.save(task));
    }

    @Transactional
    public TaskResponse addAttachment(Long id, MultipartFile file, String userEmail) {
        var task = getTaskById(id);
        validateTaskAccess(task, userEmail);

        try {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            String UPLOAD_DIR = "uploads";
            Path uploadPath = Paths.get(UPLOAD_DIR);
            Files.createDirectories(uploadPath);
            Files.copy(file.getInputStream(), uploadPath.resolve(fileName));

            var attachment = TaskAttachment.builder()
                    .task(task)
                    .fileName(file.getOriginalFilename())
                    .fileType(file.getContentType())
                    .filePath(fileName)
                    .build();

            task.getAttachments().add(attachment);
            return mapToTaskResponse(taskRepository.save(task));
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }

    @Transactional
    public void deleteTask(Long id, String userEmail) {
        var task = getTaskById(id);
        validateTaskAccess(task, userEmail);
        taskRepository.delete(task);
    }

    private Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));
    }

    private User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    private void validateTaskAccess(Task task, String userEmail) {
        if (!task.getCreatedBy().equals(userEmail) && !task.getAssignee().getEmail().equals(userEmail)) {
            throw new AccessDeniedException("You don't have access to this task");
        }
    }

    private TaskResponse mapToTaskResponse(Task task) {
        var response = new TaskResponse();
        response.setId(task.getId());
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setDueDate(task.getDueDate());
        response.setStatus(task.getStatus());
        response.setCreatedAt(task.getCreatedAt());
        response.setUpdatedAt(task.getUpdatedAt());
        response.setCreatedBy(task.getCreatedBy());
        response.setUpdatedBy(task.getUpdatedBy());

        var assigneeDto = new UserDTO();
        assigneeDto.setId(task.getAssignee().getId());
        assigneeDto.setName(task.getAssignee().getName());
        assigneeDto.setEmail(task.getAssignee().getEmail());
        response.setAssignee(assigneeDto);

        response.setComments(task.getComments().stream()
                .map(comment -> {
                    var commentDto = new CommentDTO();
                    commentDto.setId(comment.getId());
                    commentDto.setContent(comment.getContent());
                    commentDto.setCreatedAt(comment.getCreatedAt());
                    commentDto.setCreatedBy(comment.getCreatedBy());
                    return commentDto;
                })
                .toList());

        response.setAttachments(task.getAttachments().stream()
                .map(attachment -> {
                    var attachmentDto = new AttachmentDTO();
                    attachmentDto.setId(attachment.getId());
                    attachmentDto.setFileName(attachment.getFileName());
                    attachmentDto.setFileType(attachment.getFileType());
                    attachmentDto.setCreatedAt(attachment.getCreatedAt());
                    attachmentDto.setCreatedBy(attachment.getCreatedBy());
                    return attachmentDto;
                })
                .toList());

        return response;
    }
}