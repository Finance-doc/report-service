package com.financedoc.report_service.service.impl;

import com.financedoc.report_service.dto.SummaryDto;
import com.financedoc.report_service.dto.SummaryDto.CategoryExpense;
import com.financedoc.report_service.repository.ExpenseRepository;
import com.financedoc.report_service.repository.IncomeRepository;
import com.financedoc.report_service.repository.SavingRepository;
import com.financedoc.report_service.service.SummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

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
        long income = Optional.ofNullable(incomeRepository.sumByUserAndDateBetween(userId, start, end)).orElse(0L);
        long expense = Optional.ofNullable(expenseRepository.sumByUserAndDateBetween(userId, start, end)).orElse(0L);
        long saving = Optional.ofNullable(savingRepository.sumByUserAndDateBetween(userId, start, end)).orElse(0L);

        long remainingSavable = Math.max(0, income - saving); // 저축가능금액
        long net = income - expense - saving; // 순자산

        // ✅ 카테고리별 월간 지출 합계
        List<Object[]> rawCategoryExpenses = expenseRepository.sumByCategoryAndMonth(userId, ym);
        List<CategoryExpense> categoryExpenses = rawCategoryExpenses.stream()
                .map(obj -> new CategoryExpense(
                        obj[0] != null ? (String) obj[0] : "기타",
                        obj[1] != null ? ((Number) obj[1]).longValue() : 0L
                ))
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