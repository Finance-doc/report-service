package com.report.finance.service.impl;

import com.report.finance.domain.Goal;
import com.report.finance.dto.SummaryReportDto;
import com.report.finance.repository.ExpenseRepository;
import com.report.finance.repository.IncomeRepository;
import com.report.finance.repository.GoalRepository;
import com.report.finance.service.SummaryReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SummaryReportServiceImpl implements SummaryReportService {

    private final ExpenseRepository expenseRepository;
    private final IncomeRepository incomeRepository;
    private final GoalRepository goalRepository;

    @Override
    public SummaryReportDto.Res getMonthlyReport(Long userId, int year, int month) {
        YearMonth target = YearMonth.of(year, month);

        // 이번 달 총 수입/지출
        long totalExpense = expenseRepository.sumByUserAndMonth(userId, target);
        long totalIncome = incomeRepository.sumByUserAndMonth(userId, target);

        // 목표 금액 (optional)
        Goal goal = goalRepository.findByUserId(userId).orElse(null);
        Long expenseGoal = goal != null ? goal.getExpenseGoal() : null;
        Long incomeGoal = goal != null ? goal.getIncomeGoal() : null;

        // 목표 대비 달성률
        double expenseRate = (expenseGoal != null && expenseGoal > 0)
                ? ((double) totalExpense / expenseGoal) * 100 : 0.0;
        double incomeRate = (incomeGoal != null && incomeGoal > 0)
                ? ((double) totalIncome / incomeGoal) * 100 : 0.0;

        // 최근 6개월 지출 추이
        List<SummaryReportDto.Res.MonthlyExpense> last6Months = IntStream.rangeClosed(0, 5)
                .mapToObj(i -> target.minusMonths(i))
                .map(ym -> SummaryReportDto.Res.MonthlyExpense.builder()
                        .year(ym.getYear())
                        .month(ym.getMonthValue())
                        .expense(expenseRepository.sumByUserAndMonth(userId, ym))
                        .build())
                .collect(Collectors.toList());

        // 카테고리별 지출
        List<Object[]> categoryRaw = expenseRepository.sumByCategoryAndMonth(userId, target);
        List<SummaryReportDto.Res.CategoryExpense> categoryExpenses = categoryRaw.stream()
                .map(r -> SummaryReportDto.Res.CategoryExpense.builder()
                        .categoryName((String) r[0])
                        .amount((Long) r[1])
                        .build())
                .toList();

        // 재무 점수 계산 (간단 예시)
        int score = calculateFinancialScore(expenseGoal, totalExpense, incomeGoal, totalIncome);

        return SummaryReportDto.Res.builder()
                .year(year)
                .month(month)
                .totalExpense(totalExpense)
                .totalIncome(totalIncome)
                .expenseGoal(expenseGoal)
                .incomeGoal(incomeGoal)
                .expenseAchievementRate(expenseRate)
                .incomeAchievementRate(incomeRate)
                .financialScore(score)
                .last6Months(last6Months)
                .categoryExpenses(categoryExpenses)
                .build();
    }

    private int calculateFinancialScore(Long expenseGoal, long totalExpense, Long incomeGoal, long totalIncome) {
        int score = 100;

        if (expenseGoal != null && totalExpense > expenseGoal) {
            score -= Math.min(30, (int) ((totalExpense - expenseGoal) / (double) expenseGoal * 100 / 2));
        }

        if (incomeGoal != null && totalIncome < incomeGoal) {
            score -= Math.min(20, (int) ((incomeGoal - totalIncome) / (double) incomeGoal * 100 / 3));
        }

        if (score < 40) score = 40; // 최소 점수
        return score;
    }
}