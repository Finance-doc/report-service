package com.financedoc.report_service.repository;

import com.financedoc.report_service.domain.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    // 특정 날짜 지출 목록
    List<Expense> findAllByUserIdAndDateOrderByIdAsc(Long userId, LocalDate date);

    // 특정 기간 지출 목록
    List<Expense> findAllByUserIdAndDateBetweenOrderByDateAsc(Long userId, LocalDate startDate, LocalDate endDate);

    // 특정 기간 총 지출 합계
    @Query("""
        SELECT COALESCE(SUM(e.amount), 0)
        FROM Expense e
        WHERE e.userId = :userId
        AND e.date BETWEEN :startDate AND :endDate
    """)
    Long sumByUserAndDateBetween(@Param("userId") Long userId,
                                 @Param("startDate") LocalDate startDate,
                                 @Param("endDate") LocalDate endDate);

    // ✅ 월별 총 지출 합계 (SummaryReport 용)
    @Query("""
        SELECT COALESCE(SUM(e.amount), 0)
        FROM Expense e
        WHERE e.userId = :userId
        AND EXTRACT(YEAR FROM e.date) = :year
        AND EXTRACT(MONTH FROM e.date) = :month
    """)
    Long sumByUserAndMonth(@Param("userId") Long userId,
                           @Param("year") int year,
                           @Param("month") int month);

    // ✅ 카테고리별 월간 지출 합계 (SummaryReport 용)
    @Query("""
        SELECT c.name, COALESCE(SUM(e.amount), 0)
        FROM Expense e
        JOIN e.category c
        WHERE e.userId = :userId
        AND EXTRACT(YEAR FROM e.date) = :year
        AND EXTRACT(MONTH FROM e.date) = :month
        GROUP BY c.name
    """)
    List<Object[]> sumByCategoryAndMonth(@Param("userId") Long userId,
                                         @Param("year") int year,
                                         @Param("month") int month);
}