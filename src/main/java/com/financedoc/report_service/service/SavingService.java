package com.financedoc.report_service.service;

import com.financedoc.report_service.dto.SavingDto;
import java.time.LocalDate;
import java.util.List;


public interface SavingService {
    SavingDto.Res create(SavingDto.CreateReq req);
    List<SavingDto.Res> findByRange(LocalDate startDate, LocalDate endDate);
}
