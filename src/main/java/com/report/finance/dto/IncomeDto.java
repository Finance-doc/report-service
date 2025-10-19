package com.report.finance.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class IncomeDto {
    public record CreateReq(
            @NotNull Long userId,
            @NotNull LocalDate date,
            @NotNull @Positive Long amount,
            @Size(max = 255) String description
    ) {}

    public record Res(
            Long id,
            LocalDate date,
            Long amount,
            String description
    ) {}
}
