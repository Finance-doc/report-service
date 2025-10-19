package com.report.finance.service;

import com.report.finance.dto.SummaryDto;
import java.time.YearMonth;


public interface SummaryService {
    SummaryDto.MonthRes getMonthSummary(YearMonth ym, Long userId);
}