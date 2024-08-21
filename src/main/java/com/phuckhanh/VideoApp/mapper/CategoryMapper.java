package com.phuckhanh.VideoApp.mapper;

import com.phuckhanh.VideoApp.dto.request.CategoryCreationRequest;
import com.phuckhanh.VideoApp.dto.response.CategoryResponse;
import com.phuckhanh.VideoApp.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toCategory(CategoryCreationRequest request);

    CategoryResponse toCategoryResponse(Category category);
}
