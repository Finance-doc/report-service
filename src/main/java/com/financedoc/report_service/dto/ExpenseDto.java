package com.financedoc.report_service.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class ExpenseDto {
    public record CreateReq(@NotNull Long categoryId,
                            @NotNull LocalDate date,
                            @NotNull @Positive Long amount,
                            @Size(max = 255) String description) {}


    public record Res(Long id, Long categoryId, String categoryName,
                      LocalDate date, Long amount, String description) {}
}
