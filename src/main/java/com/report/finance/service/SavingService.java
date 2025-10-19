package com.report.finance.service;

import com.report.finance.dto.SavingDto;
import java.time.LocalDate;
import java.util.List;


public interface SavingService {
    SavingDto.Res create(SavingDto.CreateReq req);
    List<SavingDto.Res> findByRange(LocalDate startDate, LocalDate endDate);
}
