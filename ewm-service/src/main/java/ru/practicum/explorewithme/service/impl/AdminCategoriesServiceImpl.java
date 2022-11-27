package ru.practicum.explorewithme.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.exception.NotFoundException;
import ru.practicum.explorewithme.mapper.CategoryMapper;
import ru.practicum.explorewithme.model.Category;
import ru.practicum.explorewithme.model.dto.CategoryDto;
import ru.practicum.explorewithme.model.dto.NewCategoryDto;
import ru.practicum.explorewithme.repository.CategoriesRepository;
import ru.practicum.explorewithme.service.AdminCategoriesService;

import java.text.MessageFormat;

import static ru.practicum.explorewithme.utils.Constants.CATEGORY_NOT_FOUND_MESSAGE;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminCategoriesServiceImpl implements AdminCategoriesService {

    private final CategoriesRepository categoriesRepository;

    @Transactional
    @Override
    public CategoryDto addCategory(NewCategoryDto newCategoryDto) {
        Category category = categoriesRepository.save(CategoryMapper.toCategory(newCategoryDto));
        CategoryDto categoryDto = CategoryMapper.toCategoryDto(category);
        log.info("Added category={}", categoryDto);
        return CategoryMapper.toCategoryDto(category);
    }

    @Transactional
    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto) {
        Category category = findCategory(categoryDto.getId());
        category.setName(categoryDto.getName());
        Category categoryUpdated = categoriesRepository.save(CategoryMapper.toCategory(categoryDto));
        CategoryDto categoryUpdatedDto = CategoryMapper.toCategoryDto(categoryUpdated);
        log.info("Updated category={}", categoryUpdatedDto);
        return categoryUpdatedDto;
    }

    @Transactional
    @Override
    public void deleteCategory(Long catId) {
        findCategory(catId);
        categoriesRepository.deleteById(catId);
        log.info("Deleted category, id={}", catId);
    }

    private Category findCategory(Long id) {
        return categoriesRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(MessageFormat.format("{0}{1}", CATEGORY_NOT_FOUND_MESSAGE, id)));
    }
}
