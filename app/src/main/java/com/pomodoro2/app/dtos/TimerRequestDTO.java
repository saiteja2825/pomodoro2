package com.pomodoro2.app.dtos;

public class TimerRequestDTO {
    private Long taskId; // optional for break timer
    private int durationMin; // optional if you want to set custom durations

    // ✅ Getters and Setters
    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public int getDurationMin() {
        return durationMin;
    }

    public void setDurationMin(int durationMin) {
        this.durationMin = durationMin;
    }
}