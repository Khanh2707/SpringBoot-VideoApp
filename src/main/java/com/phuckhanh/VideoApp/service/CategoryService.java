package com.phuckhanh.VideoApp.service;

import com.phuckhanh.VideoApp.dto.request.CategoryCreationRequest;
import com.phuckhanh.VideoApp.dto.response.AccountResponse;
import com.phuckhanh.VideoApp.dto.response.CategoryResponse;
import com.phuckhanh.VideoApp.entity.Category;
import com.phuckhanh.VideoApp.exception.AppException;
import com.phuckhanh.VideoApp.exception.ErrorCode;
import com.phuckhanh.VideoApp.mapper.CategoryMapper;
import com.phuckhanh.VideoApp.repository.CategoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CategoryService {
    CategoryRepository categoryRepository;

    CategoryMapper categoryMapper;

    public List<CategoryResponse> getAllCategory() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toCategoryResponse)
                .toList();
    }

    public CategoryResponse createCategory(CategoryCreationRequest request) {
        if (categoryRepository.existsByNameCategory(request.getNameCategory()))
            throw new AppException(ErrorCode.CATEGORY_EXISTED);

        Category category = categoryMapper.toCategory(request);

        category.setIdCategory(request.getIdCategory());
        category.setNameCategory(request.getNameCategory());
        category.setDescription(request.getDescription());

        categoryRepository.save(category);

        return categoryMapper.toCategoryResponse(category);
    }
}
