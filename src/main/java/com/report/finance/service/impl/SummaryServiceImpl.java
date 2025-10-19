package com.report.finance.service.impl;

import com.report.finance.dto.SummaryDto;
import com.report.finance.dto.SummaryDto.CategoryExpense;
import com.report.finance.repository.ExpenseRepository;
import com.report.finance.repository.IncomeRepository;
import com.report.finance.repository.SavingRepository;
import com.report.finance.service.SummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SummaryServiceImpl implements SummaryService {

    private final IncomeRepository incomeRepository;
    private final ExpenseRepository expenseRepository;
    private final SavingRepository savingRepository;

    @Override
    public SummaryDto.MonthRes getMonthSummary(YearMonth ym, Long userId) {
        LocalDate start = ym.atDay(1);
        LocalDate end = ym.atEndOfMonth();

        // ✅ userId 포함된 Repository 메서드로 수정
        long income = incomeRepository.sumByUserAndDateBetween(userId, start, end);
        long expense = expenseRepository.sumByUserAndDateBetween(userId, start, end);
        long saving = savingRepository.sumByUserAndDateBetween(userId, start, end);

        long remainingSavable = Math.max(0, income - saving);
        long net = income - expense - saving;

        // ✅ 카테고리별 월간 지출 합계
        List<Object[]> rawCategoryExpenses = expenseRepository.sumByCategoryAndMonth(userId, ym);
        List<CategoryExpense> categoryExpenses = rawCategoryExpenses.stream()
                .map(obj -> new CategoryExpense((String) obj[0], ((Number) obj[1]).longValue()))
                .toList();

        return new SummaryDto.MonthRes(
                ym.getYear(),
                ym.getMonthValue(),
                income,
                expense,
                saving,
                remainingSavable,
                net,
                categoryExpenses
        );
    }
}