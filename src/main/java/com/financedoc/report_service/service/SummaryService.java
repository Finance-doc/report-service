package com.financedoc.report_service.service;

import com.financedoc.report_service.dto.SummaryDto;
import java.time.YearMonth;


public interface SummaryService {
    SummaryDto.MonthRes getMonthSummary(YearMonth ym, Long userId);
}