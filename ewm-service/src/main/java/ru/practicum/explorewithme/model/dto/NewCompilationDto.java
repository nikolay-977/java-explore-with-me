package ru.practicum.explorewithme.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewCompilationDto {
    private Boolean pinned;
    private String title;
    @JsonProperty("events")
    private List<Long> eventIdList;
}
