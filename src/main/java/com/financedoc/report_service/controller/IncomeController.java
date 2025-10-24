package com.financedoc.report_service.controller;

import com.financedoc.report_service.dto.ExpenseDto;
import com.financedoc.report_service.dto.IncomeDto;
import com.financedoc.report_service.service.IncomeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/report/api/income")
@RequiredArgsConstructor
public class IncomeController {

    private final IncomeService incomeService;

    /**
     * ✅ 수입 등록
     */
    @PostMapping
    public IncomeDto.Res create(
            @RequestHeader("X-User-Id") Long userId,
            @RequestBody IncomeDto.CreateReq req
    ) {
        // 헤더에서 받은 userId를 DTO에 주입해서 Service로 전달
        IncomeDto.CreateReq withUser = new IncomeDto.CreateReq(
                userId,
                req.date(),
                req.amount(),
                req.description()
        );
        return incomeService.create(withUser);
    }

    /**
     * ✅ 수입 조회
     * 단일 날짜 / 기간 조회 모두 지원
     */
    @GetMapping
    public List<IncomeDto.Res> list(
            @RequestHeader("X-User-Id") Long userId,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false, name = "startDate")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false, name = "endDate")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        if (date != null) {
            return incomeService.findByDate(userId, date);
        }

        if (startDate != null && endDate != null) {
            return incomeService.findByRange(userId, startDate, endDate);
        }

        // 날짜 없을 경우 오늘 기준
        return incomeService.findByDate(userId, LocalDate.now());
    }

    // 소비 단건 조회
    @GetMapping("/{incomeId}")
    public IncomeDto.Res getOne(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long incomeId
    ){
        return incomeService.findById(userId, incomeId);
    }

    // 소비 단건 삭제
    @DeleteMapping("/{incomeId}")
    public void delete(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long incomeId
    ) {
        incomeService.delete(userId, incomeId);
    }
}