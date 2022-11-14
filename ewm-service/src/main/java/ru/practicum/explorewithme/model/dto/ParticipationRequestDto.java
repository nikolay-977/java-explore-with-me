package ru.practicum.explorewithme.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParticipationRequestDto {
    private Long id;
    @JsonProperty("requester")
    private Long requesterId;
    private String created;
    private Long event;
    private String status;
}
