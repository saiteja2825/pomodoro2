
package com.pomodoro2.app.ServiceImpl;

import com.pomodoro2.app.Repositories.DailyReportRepository;
import com.pomodoro2.app.Repositories.TimerRepository;
import com.pomodoro2.app.Repositories.userRepository;
import com.pomodoro2.app.Service.DailyReportService;
import com.pomodoro2.app.entity.DailyReport;
import com.pomodoro2.app.entity.timers;
import com.pomodoro2.app.entity.users;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class DailyReportServiceImpl implements DailyReportService {

    private final DailyReportRepository dailyReportRepo;
    private final TimerRepository timerRepo;
    private final userRepository userRepo;

    public DailyReportServiceImpl(DailyReportRepository dailyReportRepo,
                                  TimerRepository timerRepo,
                                  userRepository userRepo){
        this.dailyReportRepo = dailyReportRepo;
        this.timerRepo = timerRepo;
        this.userRepo = userRepo;
    }

    @Override
    public DailyReport generateDailyReport(String username) {

        users user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("user not found"));

        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);

        List<timers> timersToday = timerRepo.findByUserIdAndStartTimeBetween(user.getId(), startOfDay, endOfDay);

        int totalPomodoros = 0;
        int totalBreaks = 0;
        int totalMinutes = 0;

        for(timers t : timersToday){
            if(t.getType().equalsIgnoreCase("WORK")) totalPomodoros++;
            else if(t.getType().equalsIgnoreCase("BREAK")) totalBreaks++;

            if(t.getEndTime() != null){
                totalMinutes += java.time.Duration.between(t.getStartTime(), t.getEndTime()).toMinutes();
            }
        }

        DailyReport report = new DailyReport();
        report.setUser(user);
        report.setDate(LocalDateTime.now());
        report.setTotalPomodoros(totalPomodoros);
        report.setTotalBreaks(totalBreaks);
        report.setTotalMinutes(totalMinutes);

        return dailyReportRepo.save(report);
    }

    @Override
    public List<DailyReport> getUserReports(String username) {
        users user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("user not found"));

        return dailyReportRepo.findByUserId(user.getId());
    }

    @Override
    public List<DailyReport> getUserReportsBetween(String username, LocalDateTime start, LocalDateTime end) {
        users user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("user not found"));

        return dailyReportRepo.findByUserIdAndDateBetween(user.getId(), start, end);
    }
}