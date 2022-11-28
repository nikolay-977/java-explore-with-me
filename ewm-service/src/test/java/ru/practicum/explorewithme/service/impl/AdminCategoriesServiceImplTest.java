package ru.practicum.explorewithme.service.impl;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.explorewithme.model.dto.CategoryDto;
import ru.practicum.explorewithme.model.dto.NewCategoryDto;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class AdminCategoriesServiceImplTest {

    @Autowired
    private AdminCategoriesServiceImpl adminCategoriesService;

    private NewCategoryDto newCategoryDto;

    @BeforeEach
    void setUp() {
        newCategoryDto = NewCategoryDto.builder()
                .name("New category")
                .build();
    }

    @Test
    void addCategory() {
        CategoryDto categoryDto = adminCategoriesService.addCategory(newCategoryDto);
        assertEquals(newCategoryDto.getName(), categoryDto.getName());
    }
}