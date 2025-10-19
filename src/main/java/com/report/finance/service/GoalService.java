package com.report.finance.service;

import com.report.finance.dto.GoalDto;

public interface GoalService {

    GoalDto.Res get(Long userId); // 현재 목표 조회

    GoalDto.Res createOrUpdate(GoalDto.Req req, Long userId); // 등록 or 수정
}
