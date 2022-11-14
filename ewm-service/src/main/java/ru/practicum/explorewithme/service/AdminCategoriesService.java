package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.model.dto.CategoryDto;
import ru.practicum.explorewithme.model.dto.NewCategoryDto;

public interface AdminCategoriesService {
    CategoryDto addCategory(NewCategoryDto categoryDto);

    CategoryDto updateCategory(CategoryDto categoryDto);

    void deleteCategory(Long catId);
}
