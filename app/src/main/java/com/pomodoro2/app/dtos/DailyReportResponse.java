package com.pomodoro2.app.dtos;

import java.time.LocalDateTime;

public class DailyReportResponse {

    private Long id;
    private LocalDateTime date;
    private int totalPomodoros;
    private int totalBreaks;
    private int totalMinutes;

    public DailyReportResponse(Long id, LocalDateTime date, int totalPomodoros, int totalBreaks, int totalMinutes) {
        this.id = id;
        this.date = date;
        this.totalPomodoros = totalPomodoros;
        this.totalBreaks = totalBreaks;
        this.totalMinutes = totalMinutes;
    }

    // ✅ Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    public int getTotalPomodoros() { return totalPomodoros; }
    public void setTotalPomodoros(int totalPomodoros) { this.totalPomodoros = totalPomodoros; }

    public int getTotalBreaks() { return totalBreaks; }
    public void setTotalBreaks(int totalBreaks) { this.totalBreaks = totalBreaks; }

    public int getTotalMinutes() { return totalMinutes; }
    public void setTotalMinutes(int totalMinutes) { this.totalMinutes = totalMinutes; }
}