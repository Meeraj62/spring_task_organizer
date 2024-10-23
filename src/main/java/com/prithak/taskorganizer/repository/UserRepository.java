package com.prithak.taskorganizer.repository;

import com.prithak.taskorganizer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
