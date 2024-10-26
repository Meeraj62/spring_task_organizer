package com.prithak.taskorganizer.repository;

import com.prithak.taskorganizer.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
