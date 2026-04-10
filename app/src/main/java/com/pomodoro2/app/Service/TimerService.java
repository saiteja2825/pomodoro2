package com.pomodoro2.app.Service;

import com.pomodoro2.app.dtos.TimerResponseDTO;

import java.util.List;

public interface TimerService {

    // ✅ Start a work timer
    TimerResponseDTO startWorkTimer(Long taskId, String username);

    // ✅ Start a break timer
    TimerResponseDTO startBreakTimer(String username);

    // ✅ Stop a timer
    TimerResponseDTO stopTime(Long timerId);

    // ✅ Get all timers for a user
    List<TimerResponseDTO> getUserTimers(String username);
}