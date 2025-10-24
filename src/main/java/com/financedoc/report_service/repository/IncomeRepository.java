package com.financedoc.report_service.repository;

import com.financedoc.report_service.domain.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public interface IncomeRepository extends JpaRepository<Income, Long> {

    // ✅ 사용자별 특정 날짜 수입 목록
    List<Income> findAllByUserIdAndDateOrderByIdAsc(Long userId, LocalDate date);

    // ✅ 사용자별 기간별 수입 목록
    List<Income> findAllByUserIdAndDateBetweenOrderByDateAsc(Long userId, LocalDate startDate, LocalDate endDate);

    // ✅ 사용자별 기간 총 수입 합계
    @Query("""
        SELECT COALESCE(SUM(i.amount), 0)
        FROM Income i
        WHERE i.userId = :userId
        AND i.date BETWEEN :startDate AND :endDate
    """)
    Long sumByUserAndDateBetween(@Param("userId") Long userId,
                                 @Param("startDate") LocalDate startDate,
                                 @Param("endDate") LocalDate endDate);

    // ✅ 월별 총 수입 합계 (SummaryReport 용)
    @Query("""
        SELECT COALESCE(SUM(i.amount), 0)
        FROM Income i
        WHERE i.userId = :userId
        AND EXTRACT(YEAR FROM i.date) = :year
        AND EXTRACT(MONTH FROM i.date) = :month
    """)
    Long sumByUserAndMonth(@Param("userId") Long userId,
                           @Param("year") int year,
                           @Param("month") int month);
}