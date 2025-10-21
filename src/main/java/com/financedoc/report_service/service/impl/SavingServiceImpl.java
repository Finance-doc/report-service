package com.financedoc.report_service.service.impl;

import com.financedoc.report_service.domain.Saving;
import com.financedoc.report_service.dto.SavingDto;
import com.financedoc.report_service.repository.IncomeRepository;
import com.financedoc.report_service.repository.SavingRepository;
import com.financedoc.report_service.service.SavingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SavingServiceImpl implements SavingService {

    private final SavingRepository savingRepository;
    private final IncomeRepository incomeRepository;

    @Override
    public SavingDto.Res create(SavingDto.CreateReq req, Long userId) {
        LocalDate date = req.date();
        YearMonth ym = YearMonth.from(date);
        LocalDate start = ym.atDay(1);
        LocalDate end = ym.atEndOfMonth();

        // ✅ 이름 통일 후 정상 동작
        long monthIncome = incomeRepository.sumByUserAndDateBetween(userId, start, end);
        long currentSaving = savingRepository.sumByUserAndDateBetween(userId, start, end);

        Saving existing = savingRepository.findByUserIdAndDate(userId, date);
        long afterSaving = currentSaving - (existing != null ? existing.getAmount() : 0) + req.amount();

        if (monthIncome <= 0) {
            throw new IllegalArgumentException("이번 달 수입이 없어 저축을 등록할 수 없습니다.");
        }
        if (afterSaving > monthIncome) {
            throw new IllegalArgumentException("이번 달 총 수입(" + monthIncome + ")을 초과하여 저축할 수 없습니다. 현재 저축: " + currentSaving);
        }

        Saving saved;
        if (existing == null) {
            saved = savingRepository.save(Saving.builder()
                    .userId(userId)
                    .date(date)
                    .amount(req.amount())
                    .build());
        } else {
            existing.setAmount(req.amount());
            saved = savingRepository.save(existing);
        }

        return new SavingDto.Res(saved.getId(), saved.getDate(), saved.getAmount());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SavingDto.Res> findByRange(Long userId, LocalDate startDate, LocalDate endDate) {
        // 유저별로 보여주려면 userId를 인자로 추가하는 게 좋음 (아래 참고)
        return savingRepository.findAllByUserIdAndDateBetweenOrderByDateAsc(userId, startDate, endDate).stream()
                .map(s -> new SavingDto.Res(s.getId(), s.getDate(), s.getAmount()))
                .toList();
    }
}