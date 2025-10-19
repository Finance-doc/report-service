package com.report.finance.controller;

import com.report.finance.service.SummaryService;
import com.report.finance.dto.SummaryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import java.time.YearMonth;


@RestController
@RequestMapping("/report/api/summary")
@RequiredArgsConstructor
public class SummaryController {
    private final SummaryService summaryService;


    @GetMapping("/month")
    public SummaryDto.MonthRes getMonth(
            @RequestHeader("X-User-Id") Long userId,
            @RequestParam int year,
            @RequestParam int month) {

        return summaryService.getMonthSummary(YearMonth.of(year, month), userId);
    }
}
