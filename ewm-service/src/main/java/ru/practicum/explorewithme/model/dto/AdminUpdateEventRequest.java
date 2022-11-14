package ru.practicum.explorewithme.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminUpdateEventRequest {
    private String annotation;
    private Boolean paid;
    private String description;
    private Boolean requestModeration;
    @JsonProperty("location")
    private LocationDto locationDto;
    @JsonProperty("category")
    private Long categoryId;
    private String title;
    private Long participantLimit;
    private String eventDate;
}
