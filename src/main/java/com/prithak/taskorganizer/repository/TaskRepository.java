package com.prithak.taskorganizer.repository;

import com.prithak.taskorganizer.entity.Task;
import com.prithak.taskorganizer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByCreatedByOrAssignee(String createdBy, User assignee);
}
