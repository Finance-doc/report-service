package com.report.finance.dto;

import com.report.finance.domain.ExpenseCategory;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

public class ExpenseCategoryDto {

    // 생성 요청 DTO
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CreateReq {
        @NotBlank(message = "카테고리 이름은 필수입니다.")
        private String name;
    }

    // 수정 요청 DTO
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UpdateReq {
        @NotBlank(message = "카테고리 이름은 필수입니다.")
        private String name;
    }

    // 응답 DTO
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Res {
        private Long id;
        private String name;

        // 엔티티 → DTO 변환
        public static Res fromEntity(ExpenseCategory category) {
            return Res.builder()
                    .id(category.getId())
                    .name(category.getName())
                    .build();
        }

        // 엔티티 리스트 → DTO 리스트 변환
        public static List<Res> fromEntityList(List<ExpenseCategory> list) {
            return list.stream()
                    .map(Res::fromEntity)
                    .toList(); // Java 17 이상
        }
    }
}