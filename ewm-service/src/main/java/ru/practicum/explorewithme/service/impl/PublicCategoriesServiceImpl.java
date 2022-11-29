package ru.practicum.explorewithme.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.exception.NotFoundException;
import ru.practicum.explorewithme.mapper.CategoryMapper;
import ru.practicum.explorewithme.model.Category;
import ru.practicum.explorewithme.model.dto.CategoryDto;
import ru.practicum.explorewithme.repository.CategoriesRepository;
import ru.practicum.explorewithme.service.PublicCategoriesService;
import ru.practicum.explorewithme.utils.CustomPageRequest;

import java.text.MessageFormat;
import java.util.List;

import static ru.practicum.explorewithme.utils.Constants.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class PublicCategoriesServiceImpl implements PublicCategoriesService {

    private final CategoriesRepository categoriesRepository;

    @Transactional(readOnly = true)
    @Override
    public List<CategoryDto> getCategories(Integer from, Integer size) {
        final Pageable pageable = CustomPageRequest.of(from, size);
        List<Category> categoryList = categoriesRepository.findAll(pageable).toList();
        if (categoryList.isEmpty()) {
            throw new NotFoundException(CATEGORIES_NOT_FOUND_MESSAGE);
        }
        List<CategoryDto> categoryDtoList = CategoryMapper.toCategoryDtoList(categoryList);
        log.info("Got categories={}", categoryDtoList);
        return categoryDtoList;
    }

    @Transactional(readOnly = true)
    @Override
    public CategoryDto getCategoryById(Long catId) {
        Category category = categoriesRepository
                .findById(catId)
                .orElseThrow(() -> new NotFoundException(MessageFormat.format(PATTERN_TWO_ARGS, CATEGORY_NOT_FOUND_MESSAGE, catId)));
        CategoryDto categoryDto = CategoryMapper.toCategoryDto(category);
        log.info("Got category={}", categoryDto);
        return categoryDto;
    }
}
