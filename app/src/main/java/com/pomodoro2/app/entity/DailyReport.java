package com.pomodoro2.app.entity;

import jakarta.persistence.Entity;
import com.pomodoro2.app.entity.users;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class DailyReport {
    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime date;

    private int totalPomodoros;

    private int totalBreaks;

    private int totalMinutes;

    @ManyToOne
    private users user;

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTotalBreaks() {
        return totalBreaks;
    }

    public void setTotalBreaks(int totalBreaks) {
        this.totalBreaks = totalBreaks;
    }

    public int getTotalMinutes() {
        return totalMinutes;
    }

    public void setTotalMinutes(int totalMinutes) {
        this.totalMinutes = totalMinutes;
    }

    public int getTotalPomodoros() {
        return totalPomodoros;
    }

    public void setTotalPomodoros(int totalPomodoros) {
        this.totalPomodoros = totalPomodoros;
    }

    public users getUser() {
        return user;
    }

    public void setUser(users user) {
        this.user = user;
    }
}
