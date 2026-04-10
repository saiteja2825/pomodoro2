package com.pomodoro2.app.Controllers;

import com.pomodoro2.app.Service.DailyReportService;
import com.pomodoro2.app.dtos.DailyReportResponse;
import com.pomodoro2.app.entity.DailyReport;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class DailyReportController {

    private final DailyReportService reportService;

    public DailyReportController(DailyReportService reportService){
        this.reportService = reportService;
    }

    private String getUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    // ✅ Generate Report
    @PostMapping("/generate")
    public ResponseEntity<DailyReportResponse> generateTodayReport(){
        String username = getUsername();
        DailyReport report = reportService.generateDailyReport(username);
        return ResponseEntity.status(201).body(mapToDto(report));
    }

    // ✅ Get All Reports
    @GetMapping
    public ResponseEntity<List<DailyReportResponse>> getAllReports(){
        String username = getUsername();
        List<DailyReport> reports = reportService.getUserReports(username);
        List<DailyReportResponse> dtoList = reports.stream()
                .map(this::mapToDto)
                .toList();
        return ResponseEntity.ok(dtoList);
    }

    // ✅ Get Reports Between Dates
    @GetMapping("/range")
    public ResponseEntity<List<DailyReportResponse>> getReportsBetween(@RequestParam String start,
                                                                       @RequestParam String end){
        String username = getUsername();
        LocalDateTime startDate = LocalDateTime.parse(start);
        LocalDateTime endDate = LocalDateTime.parse(end);
        List<DailyReport> reports = reportService.getUserReportsBetween(username, startDate, endDate);
        List<DailyReportResponse> dtoList = reports.stream()
                .map(this::mapToDto)
                .toList();
        return ResponseEntity.ok(dtoList);
    }

    // 🔥 Mapper
    private DailyReportResponse mapToDto(DailyReport report) {
        return new DailyReportResponse(
                report.getId(),
                report.getDate(),
                report.getTotalPomodoros(),
                report.getTotalBreaks(),
                report.getTotalMinutes()
        );
    }
}