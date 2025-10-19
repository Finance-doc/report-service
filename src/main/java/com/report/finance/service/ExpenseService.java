package com.report.finance.service;

import com.report.finance.dto.ExpenseDto;
import java.time.LocalDate;
import java.util.List;

public interface ExpenseService {
    ExpenseDto.Res create(ExpenseDto.CreateReq req, Long userId);
    List<ExpenseDto.Res> findByDate(Long userId, LocalDate date);
    List<ExpenseDto.Res> findByRange(Long userId, LocalDate startDate, LocalDate endDate);
}
