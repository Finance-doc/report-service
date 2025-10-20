package com.financedoc.report_service.repository;

import com.financedoc.report_service.domain.Saving;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface SavingRepository extends JpaRepository<Saving, Long> {

    List<Saving> findAllByUserIdAndDateBetweenOrderByDateAsc(Long userId, LocalDate startDate, LocalDate endDate);

    @Query("""
        SELECT COALESCE(SUM(s.amount), 0)
        FROM Saving s
        WHERE s.userId = :userId
        AND s.date BETWEEN :startDate AND :endDate
    """)
    Long sumByUserAndDateBetween(@Param("userId") Long userId,
                                 @Param("startDate") LocalDate startDate,
                                 @Param("endDate") LocalDate endDate);

    Saving findByUserIdAndDate(Long userId, LocalDate date);
}