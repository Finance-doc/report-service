package com.report.finance.repository;

import com.report.finance.domain.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GoalRepository extends JpaRepository<Goal, Long> {
    Optional<Goal> findByUserId(Long userId);
}