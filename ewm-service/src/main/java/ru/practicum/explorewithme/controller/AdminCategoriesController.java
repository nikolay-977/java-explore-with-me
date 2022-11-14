package ru.practicum.explorewithme.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.model.dto.CategoryDto;
import ru.practicum.explorewithme.model.dto.NewCategoryDto;
import ru.practicum.explorewithme.service.AdminCategoriesService;

@RestController
@RequestMapping(path = "/admin/categories")
@Slf4j
@RequiredArgsConstructor
public class AdminCategoriesController {

    private final AdminCategoriesService adminCategoriesService;

    @PatchMapping
    public CategoryDto updateCategory(@Validated @RequestBody CategoryDto categoryDto) {
        log.info("Send request PATCH: update categoryDto={}", categoryDto);
        return adminCategoriesService.updateCategory(categoryDto);
    }

    @PostMapping
    public CategoryDto addCategory(@Validated @RequestBody NewCategoryDto newCategoryDto) {
        log.info("Send request POST: add category newCategoryDto={}", newCategoryDto);
        return adminCategoriesService.addCategory(newCategoryDto);
    }

    @DeleteMapping("/{catId}")
    public void deleteCategory(@PathVariable Long catId) {
        log.info("Send request DELETE: delete category by catId={}", catId);
        adminCategoriesService.deleteCategory(catId);
    }
}
