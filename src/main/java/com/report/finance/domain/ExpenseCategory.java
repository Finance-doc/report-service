package com.report.finance.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ExpenseCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    // 로그인 사용자 구분용 (User 엔티티 연결 전까지는 userId로만 관리)
    @Column(nullable = false)
    private Long userId;

    public void update(String name) {
        this.name = name;
    }
}
