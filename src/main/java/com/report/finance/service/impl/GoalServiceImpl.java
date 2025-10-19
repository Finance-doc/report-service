package com.report.finance.service.impl;

import com.report.finance.domain.Goal;
import com.report.finance.dto.GoalDto;
import com.report.finance.repository.GoalRepository;
import com.report.finance.service.GoalService;
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
                .orElseThrow(() -> new EntityNotFoundException("사용자의 목표 금액이 설정되지 않았습니다."));
        return GoalDto.Res.fromEntity(goal);
    }

    @Override
    public GoalDto.Res createOrUpdate(GoalDto.Req req, Long userId) {
        Goal goal = goalRepository.findByUserId(userId)
                .map(existing -> {
                    existing.update(req.getIncomeGoal(), req.getExpenseGoal());
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