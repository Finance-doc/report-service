package com.financedoc.report_service.controller;

import com.financedoc.report_service.dto.SummaryReportDto;
import com.financedoc.report_service.service.SummaryReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/report/api/summary")
@RequiredArgsConstructor
public class SummaryReportController {

    private final SummaryReportService summaryReportService;

    // 월별 리포트
    @GetMapping("/report")
    public SummaryReportDto.Res getMonthlyReport(
            @RequestHeader("X-User-Id") Long userId,
            @RequestParam int year,
            @RequestParam int month) {
        return summaryReportService.getMonthlyReport(userId, year, month);
    }
}