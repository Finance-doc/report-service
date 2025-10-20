package com.financedoc.report_service.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;


import java.time.LocalDate;


@Entity
@Table(name = "income", indexes = {
        @Index(name = "idx_income_date", columnList = "date")
})
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Income {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @NotNull
    @Column(nullable = false)
    private LocalDate date;


    @NotNull @Positive
    @Column(nullable = false)
    private Long amount; // 원화 정수


    @Size(max = 255)
    private String description;
}