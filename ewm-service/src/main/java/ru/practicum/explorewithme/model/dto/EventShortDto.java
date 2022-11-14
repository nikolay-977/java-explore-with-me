package ru.practicum.explorewithme.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

import static ru.practicum.explorewithme.utils.DateHelper.DATE_TIME_FORMAT;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class EventShortDto {
    private Long id;
    private String annotation;
    @JsonProperty("initiator")
    private UserDto initiatorDto;
    private Boolean paid;
    @JsonProperty("category")
    private CategoryDto categoryDto;
    private String title;
    private Long confirmedRequests;
    private Long views;
    @JsonFormat(pattern = DATE_TIME_FORMAT)
    private LocalDateTime eventDate;
}