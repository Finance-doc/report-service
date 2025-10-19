package com.report.finance.service.impl;


import com.report.finance.domain.ExpenseCategory;
import com.report.finance.repository.ExpenseCategoryRepository;
import com.report.finance.service.ExpenseCategoryService;
import com.report.finance.dto.ExpenseCategoryDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ExpenseCategoryServiceImpl implements ExpenseCategoryService {

    private final ExpenseCategoryRepository categoryRepository;

    @Override
    public ExpenseCategoryDto.Res create(ExpenseCategoryDto.CreateReq req, Long userId) {
        ExpenseCategory category = ExpenseCategory.builder()
                .name(req.getName())
                .userId(userId)
                .build();
        categoryRepository.save(category);
        return ExpenseCategoryDto.Res.fromEntity(category);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExpenseCategoryDto.Res> list(Long userId) {
        List<ExpenseCategory> list = categoryRepository.findByUserId(userId);
        return ExpenseCategoryDto.Res.fromEntityList(list);
    }

    @Override
    @Transactional(readOnly = true)
    public ExpenseCategoryDto.Res get(Long id, Long userId) {
        ExpenseCategory category = findCategory(id);
        validateOwnership(category, userId);
        return ExpenseCategoryDto.Res.fromEntity(category);
    }

    @Override
    public ExpenseCategoryDto.Res update(Long id, ExpenseCategoryDto.UpdateReq req, Long userId) {
        ExpenseCategory category = findCategory(id);
        validateOwnership(category, userId);
        category.update(req.getName());
        return ExpenseCategoryDto.Res.fromEntity(category);
    }

    @Override
    public void delete(Long id, Long userId) {
        ExpenseCategory category = findCategory(id);
        validateOwnership(category, userId);
        categoryRepository.delete(category);
    }

    private ExpenseCategory findCategory(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("카테고리를 찾을 수 없습니다. id=" + id));
    }

    private void validateOwnership(ExpenseCategory category, Long userId) {
        if (!category.getUserId().equals(userId)) {
            throw new SecurityException("본인 소유의 카테고리만 수정/삭제할 수 있습니다.");
        }
    }
}