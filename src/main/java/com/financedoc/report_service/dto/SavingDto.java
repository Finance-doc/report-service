package com.financedoc.report_service.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class SavingDto {

    // 요청 DTO (저축 등록/수정용)
    public record CreateReq(
            @NotNull Long userId,
            @NotNull LocalDate date,
            @NotNull @Positive Long amount
    ) {}

    // 응답 DTO (조회용)
    public record Res(
            Long id,
            LocalDate date,
            Long amount
    ) {}
}