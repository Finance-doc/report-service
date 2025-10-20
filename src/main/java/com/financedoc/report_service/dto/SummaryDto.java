package com.financedoc.report_service.dto;

import java.util.List;

public class SummaryDto {

    public record CategoryExpense(
            String category,
            long amount
    ) {}

    public record MonthRes(int year,
                           int month,
                           long totalIncome,
                           long totalExpense,
                           long totalSaving,
                           long remainingSavable, // 아직 저축으로 할당 가능 (수입 - 저축)
                           long net, // 순자산 증감 = 수입 - 지출 - 저축
                           List<CategoryExpense> categoryExpenses
    ) { }
}
