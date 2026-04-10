package com.pomodoro2.app.Repositories;


import com.pomodoro2.app.entity.DailyReport;
import com.pomodoro2.app.entity.users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface DailyReportRepository extends JpaRepository<DailyReport, Long> {
    List<DailyReport> findByUserId(Long userId);
    List<DailyReport> findByUserIdAndDateBetween(Long userId, LocalDateTime start, LocalDateTime end);
}