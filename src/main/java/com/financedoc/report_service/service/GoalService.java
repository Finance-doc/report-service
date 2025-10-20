package com.financedoc.report_service.service;

import com.financedoc.report_service.dto.GoalDto;

public interface GoalService {

    GoalDto.Res get(Long userId); // 현재 목표 조회

    GoalDto.Res createOrUpdate(GoalDto.Req req, Long userId); // 등록 or 수정
}
