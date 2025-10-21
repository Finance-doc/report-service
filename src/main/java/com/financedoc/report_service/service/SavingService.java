package com.financedoc.report_service.service;

import com.financedoc.report_service.dto.SavingDto;
import java.time.LocalDate;
import java.util.List;


public interface SavingService {
    SavingDto.Res create(SavingDto.CreateReq req, Long userId);
    List<SavingDto.Res> findByRange(Long userId, LocalDate startDate, LocalDate endDate);
}
