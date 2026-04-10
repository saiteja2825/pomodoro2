package com.pomodoro2.app.Repositories;

import com.pomodoro2.app.entity.tasks;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<tasks,Long> {
    List<tasks> findByUserId(Long userId);
}
