package com.financedoc.report_service.controller;

import com.financedoc.report_service.service.SavingService;
import com.financedoc.report_service.dto.SavingDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import java.time.YearMonth;
import java.util.List;


@RestController
@RequestMapping("/report/api/saving")
@RequiredArgsConstructor
public class SavingController {

    private final SavingService savingService;


    @PostMapping
    public SavingDto.Res create(@RequestBody @Valid SavingDto.CreateReq req) {
        return savingService.create(req);
    }


    @GetMapping
    public List<SavingDto.Res> list(@RequestParam int year, @RequestParam int month) {
        YearMonth ym = YearMonth.of(year, month);
        return savingService.findByRange(ym.atDay(1), ym.atEndOfMonth());
    }
}
