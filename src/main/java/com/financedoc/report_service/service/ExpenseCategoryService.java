package com.financedoc.report_service.service;

import com.financedoc.report_service.dto.ExpenseCategoryDto;
import java.util.List;

public interface ExpenseCategoryService {
    ExpenseCategoryDto.Res create(ExpenseCategoryDto.CreateReq req, Long userId);
    List<ExpenseCategoryDto.Res> list(Long userId);
    ExpenseCategoryDto.Res get(Long id, Long userId);
    ExpenseCategoryDto.Res update(Long id, ExpenseCategoryDto.UpdateReq req, Long userId);
    void delete(Long id, Long userId);
}
