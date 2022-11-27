package ru.practicum.explorewithme.model.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationDto {
    private Long id;
    @NotNull
    private Double lat;
    @NotNull
    private Double lon;
}