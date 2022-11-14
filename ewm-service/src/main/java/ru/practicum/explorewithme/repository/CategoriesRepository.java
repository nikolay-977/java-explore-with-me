package ru.practicum.explorewithme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explorewithme.model.Category;

public interface CategoriesRepository extends JpaRepository<Category, Long> {
}
