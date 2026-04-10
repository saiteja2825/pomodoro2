package com.pomodoro2.app.Controllers;

import com.pomodoro2.app.Service.TimerService;
import com.pomodoro2.app.dtos.TimerResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/timers")
public class TimerController {

    private final TimerService timerService;

    public TimerController(TimerService timerService){
        this.timerService = timerService;
    }

    // 🔥 Get username from SecurityContext
    private String getUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    // ✅ Start Work Timer
    @PostMapping("/work")
    public ResponseEntity<TimerResponseDTO> startWorkTimer(@RequestParam Long taskId){
        String username = getUsername();
        TimerResponseDTO timer = timerService.startWorkTimer(taskId, username);
        return ResponseEntity.status(201).body(timer);
    }

    // ✅ Start Break Timer
    @PostMapping("/break")
    public ResponseEntity<TimerResponseDTO> startBreakTimer(){
        String username = getUsername();
        TimerResponseDTO timer = timerService.startBreakTimer(username);
        return ResponseEntity.status(201).body(timer);
    }

    // ✅ Stop Timer
    @PutMapping("/{timerId}/stop")
    public ResponseEntity<TimerResponseDTO> stopTimer(@PathVariable Long timerId){
        TimerResponseDTO timer = timerService.stopTime(timerId);
        return ResponseEntity.ok(timer);
    }

    // ✅ Get My Timers
    @GetMapping("/my")
    public ResponseEntity<List<TimerResponseDTO>> getMyTimers(){
        String username = getUsername();
        List<TimerResponseDTO> timersList = timerService.getUserTimers(username);
        return ResponseEntity.ok(timersList);
    }
}