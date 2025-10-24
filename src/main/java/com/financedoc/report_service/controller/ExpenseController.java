package com.financedoc.report_service.controller;

import com.financedoc.report_service.service.ExpenseService;
import com.financedoc.report_service.dto.ExpenseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/report/api/expense")
@RequiredArgsConstructor
public class ExpenseController {


    private final ExpenseService expenseService;

    // 지출등록
    @PostMapping
    public ExpenseDto.Res create(
            @RequestHeader("X-User-Id") Long userId,
            @RequestBody @Valid ExpenseDto.CreateReq req) {
        return expenseService.create(req, userId);
    }

    // 지출목록조회
    @GetMapping
    public List<ExpenseDto.Res> list(
            @RequestHeader("X-User-Id") Long userId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false, name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false, name = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        if (date != null) return expenseService.findByDate(userId, date);
        if (startDate != null && endDate != null) return expenseService.findByRange(userId, startDate, endDate);
        return expenseService.findByDate(userId, LocalDate.now());
    }

    // 지출 단건 조회
    @GetMapping("/{expenseId}")
    public ExpenseDto.Res getOne(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long expenseId
    ){
        return expenseService.findById(userId, expenseId);
    }

    // ✅ 지출 단건 삭제
    @DeleteMapping("/{expenseId}")
    public void delete(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long expenseId
    ) {
        expenseService.delete(userId, expenseId);
    }
}

