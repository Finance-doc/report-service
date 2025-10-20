package com.financedoc.report_service.service.impl;

import com.financedoc.report_service.domain.Income;
import com.financedoc.report_service.dto.IncomeDto;
import com.financedoc.report_service.repository.IncomeRepository;
import com.financedoc.report_service.service.IncomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class IncomeServiceImpl implements IncomeService {

    private final IncomeRepository incomeRepository;

    @Override
    public IncomeDto.Res create(IncomeDto.CreateReq req) {
        Income saved = incomeRepository.save(Income.builder()
                .userId(req.userId()) // ✅ userId 추가
                .date(req.date())
                .amount(req.amount())
                .description(req.description())
                .build());

        return new IncomeDto.Res(
                saved.getId(),
                saved.getDate(),
                saved.getAmount(),
                saved.getDescription()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<IncomeDto.Res> findByDate(Long userId, LocalDate date) {
        return incomeRepository.findAllByUserIdAndDateOrderByIdAsc(userId, date).stream()
                .map(i -> new IncomeDto.Res(
                        i.getId(),
                        i.getDate(),
                        i.getAmount(),
                        i.getDescription()
                ))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<IncomeDto.Res> findByRange(Long userId, LocalDate startDate, LocalDate endDate) {
        return incomeRepository.findAllByUserIdAndDateBetweenOrderByDateAsc(userId, startDate, endDate).stream()
                .map(i -> new IncomeDto.Res(
                        i.getId(),
                        i.getDate(),
                        i.getAmount(),
                        i.getDescription()
                ))
                .toList();
    }
}