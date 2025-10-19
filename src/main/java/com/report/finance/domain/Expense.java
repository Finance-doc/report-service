package com.report.finance.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;


import java.time.LocalDate;


@Entity
@Table(name = "expense", indexes = {
        @Index(name = "idx_expense_date", columnList = "date")
})
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Expense {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private ExpenseCategory category;


    @NotNull
    @Column(nullable = false)
    private LocalDate date;


    /** 금액(원) – 정수로 저장 */
    @NotNull @Positive
    @Column(nullable = false)
    private Long amount;


    @Size(max = 255)
    private String description;
}
