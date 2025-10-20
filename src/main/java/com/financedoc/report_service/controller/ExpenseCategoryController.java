package com.financedoc.report_service.controller;

import com.financedoc.report_service.service.ExpenseCategoryService;
import com.financedoc.report_service.dto.ExpenseCategoryDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/report/api/categories")
@RequiredArgsConstructor
public class ExpenseCategoryController {

    private final ExpenseCategoryService categoryService;

    // 생성
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ExpenseCategoryDto.Res create(
            @RequestHeader("X-User-Id") Long userId, // Gateway에서 전달되는 헤더
            @RequestBody @Valid ExpenseCategoryDto.CreateReq req) {

        return categoryService.create(req, userId);
    }

    // 전체 조회
    @GetMapping
    public List<ExpenseCategoryDto.Res> list(@RequestHeader("X-User-Id") Long userId) {
        return categoryService.list(userId);
    }

    // 단건 조회
    @GetMapping("/{id}")
    public ExpenseCategoryDto.Res get(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long id) {

        return categoryService.get(id, userId);
    }

    // 수정
    @PutMapping("/{id}")
    public ExpenseCategoryDto.Res update(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long id,
            @RequestBody @Valid ExpenseCategoryDto.UpdateReq req) {

        return categoryService.update(id, req, userId);
    }

    // 삭제
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long id) {

        categoryService.delete(id, userId);
    }
}