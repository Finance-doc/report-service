package com.financedoc.report_service.service;

import com.financedoc.report_service.dto.IncomeDto;
import java.time.LocalDate;
import java.util.List;

public interface IncomeService {
    IncomeDto.Res create(IncomeDto.CreateReq req);
    List<IncomeDto.Res> findByDate(Long userId, LocalDate date);
    List<IncomeDto.Res> findByRange(Long userId, LocalDate startDate, LocalDate endDate);
}