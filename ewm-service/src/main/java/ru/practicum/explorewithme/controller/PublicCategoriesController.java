package ru.practicum.explorewithme.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.model.dto.CategoryDto;
import ru.practicum.explorewithme.service.PublicCategoriesService;

import java.util.List;

@RestController
@RequestMapping("/categories")
@Slf4j
@RequiredArgsConstructor
public class PublicCategoriesController {
    private final PublicCategoriesService publicCategoriesService;

    @GetMapping
    public List<CategoryDto> getAllCategories(@RequestParam(name = "from", defaultValue = "0") Integer from,
                                              @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Send request GET: get categories with from={}, size={}", from, size);
        return publicCategoriesService.getCategories(from, size);
    }

    @GetMapping("/{catId}")
    public CategoryDto getCategoryById(@PathVariable Long catId) {
        log.info("Send request GET: get category by catId={}", catId);
        return publicCategoriesService.getCategoryById(catId);
    }
}
