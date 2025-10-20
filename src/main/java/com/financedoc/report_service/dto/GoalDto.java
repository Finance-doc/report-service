package com.financedoc.report_service.dto;

import com.financedoc.report_service.domain.Goal;
import lombok.*;

public class GoalDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Req {
        private Long incomeGoal;
        private Long expenseGoal;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Res {
        private Long incomeGoal;
        private Long expenseGoal;

        public static Res fromEntity(Goal goal) {
            return Res.builder()
                    .incomeGoal(goal.getIncomeGoal())
                    .expenseGoal(goal.getExpenseGoal())
                    .build();
        }
    }
}