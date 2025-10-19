package com.report.finance.repository;

import com.report.finance.domain.ExpenseCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseCategoryRepository extends JpaRepository<ExpenseCategory, Long> {
    List<ExpenseCategory> findByUserId(Long userId);
}
