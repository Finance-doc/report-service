package com.report.finance.service.impl;

import com.report.finance.domain.Expense;
import com.report.finance.domain.ExpenseCategory;
import com.report.finance.repository.ExpenseCategoryRepository;
import com.report.finance.repository.ExpenseRepository;
import com.report.finance.service.ExpenseService;
import com.report.finance.dto.ExpenseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseCategoryRepository categoryRepository;

    @Override
    public ExpenseDto.Res create(ExpenseDto.CreateReq req, Long userId) {
        ExpenseCategory category = categoryRepository.findById(req.categoryId())
                .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다: id=" + req.categoryId()));

        Expense saved = expenseRepository.save(Expense.builder()
                .userId(userId)
                .category(category)
                .date(req.date())
                .amount(req.amount())
                .description(req.description())
                .build());

        return new ExpenseDto.Res(
                saved.getId(),
                category.getId(),
                category.getName(),
                saved.getDate(),
                saved.getAmount(),
                saved.getDescription()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExpenseDto.Res> findByDate(Long userId, LocalDate date) {
        return expenseRepository.findAllByUserIdAndDateOrderByIdAsc(userId, date).stream()
                .map(e -> new ExpenseDto.Res(
                        e.getId(),
                        e.getCategory().getId(),
                        e.getCategory().getName(),
                        e.getDate(),
                        e.getAmount(),
                        e.getDescription()
                ))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExpenseDto.Res> findByRange(Long userId, LocalDate startDate, LocalDate endDate) {
        return expenseRepository.findAllByUserIdAndDateBetweenOrderByDateAsc(userId, startDate, endDate).stream()
                .map(e -> new ExpenseDto.Res(
                        e.getId(),
                        e.getCategory().getId(),
                        e.getCategory().getName(),
                        e.getDate(),
                        e.getAmount(),
                        e.getDescription()
                ))
                .toList();
    }
}