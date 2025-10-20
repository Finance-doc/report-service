package com.financedoc.report_service.service;

import com.financedoc.report_service.dto.SummaryReportDto;

public interface SummaryReportService {
    SummaryReportDto.Res getMonthlyReport(Long userId, int year, int month);
}