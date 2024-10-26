package com.prithak.taskorganizer.repository;

import com.prithak.taskorganizer.entity.Task;
import com.prithak.taskorganizer.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByTaskOrderByCreatedAtDesc(Task task);
}
