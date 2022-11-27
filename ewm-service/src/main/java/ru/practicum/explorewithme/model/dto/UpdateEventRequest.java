package ru.practicum.explorewithme.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Optional;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEventRequest {
    private Long eventId;
    private Optional<String> annotation;
    private Optional<Boolean> paid;
    private Optional<String> description;
    @JsonProperty("category")
    private Optional<Long> categoryId;
    private Optional<String> title;
    private Optional<Long> participantLimit;
    private Optional<String> eventDate;
}
