package com.financedoc.report_service.service.impl;

import com.financedoc.report_service.domain.Goal;
import com.financedoc.report_service.dto.GoalDto;
import com.financedoc.report_service.repository.GoalRepository;
import com.financedoc.report_service.service.GoalService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class GoalServiceImpl implements GoalService {

    private final GoalRepository goalRepository;

    @Override
    @Transactional(readOnly = true)
    public GoalDto.Res get(Long userId) {
        Goal goal = goalRepository.findByUserId(userId)
                .orElseGet(() -> Goal.builder()
                        .userId(userId)
                        .incomeGoal(0L)
                        .expenseGoal(0L)
                        .build() // 기본값으로 세팅된 Goal 반환
                );
        return GoalDto.Res.fromEntity(goal);
    }

    @Override
    public GoalDto.Res createOrUpdate(GoalDto.Req req, Long userId) {
        Goal goal = goalRepository.findByUserId(userId)
                .map(existing -> {
                    if (req.getIncomeGoal() != null) {
                        existing.setIncomeGoal(req.getIncomeGoal());
                    }
                    if (req.getExpenseGoal() != null) {
                        existing.setExpenseGoal(req.getExpenseGoal());
                    }
                    return existing;
                })
                .orElseGet(() -> Goal.builder()
                        .userId(userId)
                        .incomeGoal(req.getIncomeGoal())
                        .expenseGoal(req.getExpenseGoal())
                        .build());

        goalRepository.save(goal);
        return GoalDto.Res.fromEntity(goal);
    }
}