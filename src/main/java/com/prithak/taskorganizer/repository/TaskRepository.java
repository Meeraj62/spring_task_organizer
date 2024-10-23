package com.prithak.taskorganizer.repository;

import com.prithak.taskorganizer.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
