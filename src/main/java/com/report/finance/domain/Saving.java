package com.report.finance.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;


@Entity
@Table(name = "saving", indexes = {
        @Index(name = "idx_saving_date", columnList = "date")
})
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Saving {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @NotNull
    @Column(nullable = false)
    private LocalDate date; // 저축 일자


    @NotNull @Positive
    @Column(nullable = false)
    private Long amount; // 저축 금액(원)

}
