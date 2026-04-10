package com.pomodoro2.app.ServiceImpl;

import com.pomodoro2.app.Repositories.TaskRepository;
import com.pomodoro2.app.Repositories.TimerRepository;
import com.pomodoro2.app.Repositories.userRepository;
import com.pomodoro2.app.Service.TimerService;
import com.pomodoro2.app.dtos.TimerResponseDTO;
import com.pomodoro2.app.entity.tasks;
import com.pomodoro2.app.entity.timers;
import com.pomodoro2.app.entity.users;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TimerServiceImpl implements TimerService {

    private final TimerRepository timerRepo;
    private final userRepository userRepo;
    private final TaskRepository taskRepo;

    public TimerServiceImpl(TimerRepository timerRepo,
                            userRepository userRepo,
                            TaskRepository taskRepo){
        this.timerRepo = timerRepo;
        this.userRepo = userRepo;
        this.taskRepo = taskRepo;
    }

    // ✅ Start Work Timer
    @Override
    public TimerResponseDTO startWorkTimer(Long taskId, String username) {

        users existingUser = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("user not found"));

        tasks existingTask = taskRepo.findById(taskId)
                .orElseThrow(() -> new RuntimeException("task not found"));

        timers timer = new timers();
        timer.setType("WORK");
        timer.setStatus("RUNNING");
        timer.setStartTime(LocalDateTime.now());
        timer.setUser(existingUser);
        timer.setTask(existingTask);

        timers saved = timerRepo.save(timer);
        return mapToDTO(saved);
    }

    // ✅ Start Break Timer
    @Override
    public TimerResponseDTO startBreakTimer(String username) {

        users existingUser = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("user not found"));

        timers timer = new timers();
        timer.setType("BREAK");
        timer.setStatus("RUNNING");
        timer.setStartTime(LocalDateTime.now());
        timer.setUser(existingUser);

        timers saved = timerRepo.save(timer);
        return mapToDTO(saved);
    }

    // ✅ Stop Timer
    @Override
    public TimerResponseDTO stopTime(Long timerId) {

        timers existingTimer = timerRepo.findById(timerId)
                .orElseThrow(() -> new RuntimeException("timer not found"));

        existingTimer.setEndTime(LocalDateTime.now());
        existingTimer.setStatus("COMPLETED");

        timers saved = timerRepo.save(existingTimer);
        return mapToDTO(saved);
    }

    // ✅ Get User Timers
    @Override
    public List<TimerResponseDTO> getUserTimers(String username) {

        users existingUser = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("user not found"));

        List<timers> timersList = timerRepo.findByUserId(existingUser.getId());

        return timersList.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // 🔥 Helper: Map entity to DTO
    private TimerResponseDTO mapToDTO(timers timer) {
        TimerResponseDTO dto = new TimerResponseDTO();
        dto.setId(timer.getId());
        dto.setType(timer.getType());
        dto.setStatus(timer.getStatus());
        dto.setStartTime(timer.getStartTime());
        dto.setEndTime(timer.getEndTime());
        dto.setUsername(timer.getUser().getUsername());

        if ("WORK".equals(timer.getType()) && timer.getTask() != null) {
            dto.setTaskId(timer.getTask().getId());
            dto.setTaskTitle(timer.getTask().getTitle());
        }

        return dto;
    }
}