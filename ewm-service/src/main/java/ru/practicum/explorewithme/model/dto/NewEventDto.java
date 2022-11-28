package ru.practicum.explorewithme.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewEventDto {
    private String annotation;
    @JsonProperty("location")
    private LocationDto locationDto;
    private Boolean paid;
    private String description;
    private Boolean requestModeration;
    @JsonProperty("category")
    private Long categoryId;
    private String title;
    private Long participantLimit;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
}
