package ru.practicum.explorewithme.model.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CompilationDto {
    private Long id;
    private Boolean pinned;
    private String title;
    private List<EventFullDto> events;
}