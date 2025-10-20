package com.financedoc.report_service.dto;

import lombok.*;

import java.util.List;

public class SummaryReportDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Res {

        private int year;
        private int month;

        // 이번 달 통계
        private long totalExpense;
        private long totalIncome;

        // 목표 대비
        private Long expenseGoal;
        private Long incomeGoal;
        private double expenseAchievementRate; // 지출 목표 달성률 (%)
        private double incomeAchievementRate;  // 수입 목표 달성률 (%)

        // 재무 점수
        private int financialScore;

        // 최근 6개월 지출 추이
        private List<MonthlyExpense> last6Months;

        // 카테고리별 지출
        private List<CategoryExpense> categoryExpenses;

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        public static class MonthlyExpense {
            private int year;
            private int month;
            private long expense;
        }

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        public static class CategoryExpense {
            private String categoryName;
            private long amount;
        }
    }
}
