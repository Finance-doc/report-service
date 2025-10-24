package com.financedoc.report_service.service.impl;

import com.financedoc.report_service.domain.Expense;
import com.financedoc.report_service.domain.ExpenseCategory;
import com.financedoc.report_service.repository.ExpenseCategoryRepository;
import com.financedoc.report_service.repository.ExpenseRepository;
import com.financedoc.report_service.service.ExpenseService;
import com.financedoc.report_service.dto.ExpenseDto;
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

    @Override
    @Transactional(readOnly = true)
    public ExpenseDto.Res findById(Long userId, Long expenseId) {
        Expense expense = expenseRepository.findById(expenseId)
                .filter(e -> e.getUserId().equals(userId))
                .orElseThrow(() -> new IllegalArgumentException("지출 내역을 찾을 수 없습니다: id=" + expenseId));
        return new ExpenseDto.Res(
                expense.getId(),
                expense.getCategory().getId(),
                expense.getCategory().getName(),
                expense.getDate(),
                expense.getAmount(),
                expense.getDescription()
        );
    }

    @Override
    public void delete(Long userId, Long expenseId) {
        Expense expense = expenseRepository.findById(expenseId)
                .filter(e -> e.getUserId().equals(userId))
                .orElseThrow(() -> new IllegalArgumentException("지출 내역을 찾을 수 없습니다: id=" + expenseId));
        expenseRepository.delete(expense);
    }
}