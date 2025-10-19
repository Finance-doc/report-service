package com.report.finance.controller;

import com.report.finance.dto.GoalDto;
import com.report.finance.service.GoalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/report/api/goal")
@RequiredArgsConstructor
public class GoalController {

    private final GoalService goalService;

    // 목표 조회 (홈화면 표시용)
    @GetMapping
    public GoalDto.Res getGoal(@RequestHeader("X-User-Id") Long userId) {
        return goalService.get(userId);
    }

    // 목표 등록 및 수정
    @PostMapping
    public GoalDto.Res createOrUpdateGoal(
            @RequestHeader("X-User-Id") Long userId,
            @RequestBody GoalDto.Req req) {
        return goalService.createOrUpdate(req, userId);
    }
}