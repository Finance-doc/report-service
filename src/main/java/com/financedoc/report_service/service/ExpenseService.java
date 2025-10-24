package com.financedoc.report_service.service;

import com.financedoc.report_service.dto.ExpenseDto;
import java.time.LocalDate;
import java.util.List;

public interface ExpenseService {
    ExpenseDto.Res create(ExpenseDto.CreateReq req, Long userId);
    List<ExpenseDto.Res> findByDate(Long userId, LocalDate date);
    List<ExpenseDto.Res> findByRange(Long userId, LocalDate startDate, LocalDate endDate);
    ExpenseDto.Res findById(Long userId, Long expenseId);
    void delete(Long userId, Long expenseId);
}
