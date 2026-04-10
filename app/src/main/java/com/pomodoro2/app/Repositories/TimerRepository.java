package com.pomodoro2.app.Repositories;

import com.pomodoro2.app.entity.timers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TimerRepository extends JpaRepository<timers,Long> {
    List<timers> findByUserId(Long userId);

    List<timers> findByUserIdAndStartTimeBetween(Long id, LocalDateTime startOfDay, LocalDateTime endOfDay);
}
