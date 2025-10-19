package com.report.finance.service;

import com.report.finance.dto.SummaryReportDto;

public interface SummaryReportService {
    SummaryReportDto.Res getMonthlyReport(Long userId, int year, int month);
}