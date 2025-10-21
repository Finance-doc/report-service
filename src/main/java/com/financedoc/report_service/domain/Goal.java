package com.financedoc.report_service.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId; // Gateway에서 전달된 사용자 ID

    private Long incomeGoal;  // 소득 목표 금액
    private Long expenseGoal; // 지출 목표 금액

    public void update(Long incomeGoal, Long expenseGoal) {
        if (incomeGoal != null) this.incomeGoal = incomeGoal;
        if (expenseGoal != null) this.expenseGoal = expenseGoal;
    }
}
