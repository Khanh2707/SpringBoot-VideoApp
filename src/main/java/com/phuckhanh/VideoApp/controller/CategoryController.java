package com.phuckhanh.VideoApp.controller;

import com.phuckhanh.VideoApp.dto.request.AccountCreationRequest;
import com.phuckhanh.VideoApp.dto.request.CategoryCreationRequest;
import com.phuckhanh.VideoApp.dto.response.AccountResponse;
import com.phuckhanh.VideoApp.dto.response.ApiResponse;
import com.phuckhanh.VideoApp.dto.response.CategoryResponse;
import com.phuckhanh.VideoApp.service.CategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CategoryController {
    CategoryService categoryService;

    @GetMapping("")
    ApiResponse<List<CategoryResponse>> getAllCategory() {
        return ApiResponse.<List<CategoryResponse>>builder()
                .result(categoryService.getAllCategory())
                .build();
    }

    @PostMapping("")
    ApiResponse<CategoryResponse> createCategory(@RequestBody CategoryCreationRequest request) {
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.createCategory(request))
                .build();
    }
}
