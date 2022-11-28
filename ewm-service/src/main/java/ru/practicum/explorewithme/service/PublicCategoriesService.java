package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.model.dto.CategoryDto;

import java.util.List;

public interface PublicCategoriesService {
    List<CategoryDto> getCategories(Integer from, Integer size);

    CategoryDto getCategoryById(Long catId);
}
