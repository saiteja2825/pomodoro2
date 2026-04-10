package com.pomodoro2.app.Service;



import com.pomodoro2.app.entity.DailyReport;

import java.time.LocalDateTime;
import java.util.List;

public interface DailyReportService {
    DailyReport generateDailyReport(String username); // generate report for today
    List<DailyReport> getUserReports(String username); // all reports
    List<DailyReport> getUserReportsBetween(String username, LocalDateTime start, LocalDateTime end); // range
}