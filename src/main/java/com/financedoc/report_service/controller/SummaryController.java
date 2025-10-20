package com.financedoc.report_service.controller;

import com.financedoc.report_service.service.SummaryService;
import com.financedoc.report_service.dto.SummaryDto;
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
