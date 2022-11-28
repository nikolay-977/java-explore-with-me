package ru.practicum.explorewithme.model.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewCategoryDto {
    @NotNull
    private String name;
}
