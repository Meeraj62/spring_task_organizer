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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TaskService taskService;

    private Task testTask;
    private TaskDTO testTaskDTO;
    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testUser");

        testTask = new Task();
        testTask.setId(1L);
        testTask.setTitle("Test Prithak");
        testTask.setDescription("Prithak Description");
        testTask.setDueDate(LocalDate.now());
        testTask.setStatus(TaskStatus.OPEN);
        testTask.setAssignee(testUser);

        testTaskDTO = new TaskDTO();
        testTaskDTO.setId(1L);
        testTaskDTO.setTitle("Test Prithak");
        testTaskDTO.setDescription("Prithak Description");
        testTaskDTO.setDueDate(LocalDate.now());
        testTaskDTO.setStatus(TaskStatus.OPEN);
        testTaskDTO.setAssignee(testUser);
    }

    @Test
    void createTask_Success() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(testUser));
        when(taskRepository.save(any(Task.class))).thenReturn(testTask);

        TaskDTO result = taskService.createTask(testTaskDTO, "testUser");

        assertNotNull(result);
        assertEquals(testTaskDTO.getTitle(), result.getTitle());
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void createTask_UserNotFound() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> taskService.createTask(testTaskDTO, "noUser"));
    }

    @Test
    void getTaskById_Success() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(testTask));

        Optional<TaskDTO> result = taskService.getTaskById(1L);

        assertTrue(result.isPresent());
        assertEquals(testTask.getTitle(), result.get().getTitle());
    }

    @Test
    void getAllTasks_Success() {
        when(taskRepository.findAll()).thenReturn(Collections.singletonList(testTask));

        List<TaskDTO> result = taskService.getAllTasks();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(testTask.getTitle(), result.get(0).getTitle());
    }

    @Test
    void updateTask_Success() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(testTask));
        when(taskRepository.save(any(Task.class))).thenReturn(testTask);

        Optional<TaskDTO> result = taskService.updateTask(1L, testTaskDTO);

        assertTrue(result.isPresent());
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void transitionStatus_Success() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(testTask));
        when(taskRepository.save(any(Task.class))).thenReturn(testTask);

        Optional<TaskDTO> result = taskService.transitionStatus(1L, TaskStatus.IN_PROGRESS);

        assertTrue(result.isPresent());
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void addCommentToTask_Success() {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setContent("Test Comment");
        when(taskRepository.findById(1L)).thenReturn(Optional.of(testTask));
        when(commentRepository.save(any(Comment.class))).thenReturn(new Comment());

        Optional<CommentDTO> result = taskService.addCommentToTask(1L, commentDTO, "testUser");

        assertTrue(result.isPresent());
        verify(commentRepository).save(any(Comment.class));
    }

    @Test
    void deleteTask_Success() {
        when(taskRepository.existsById(1L)).thenReturn(true);

        taskService.deleteTask(1L);

        verify(taskRepository).deleteById(1L);
    }
}
