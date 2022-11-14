package ru.practicum.explorewithme.model.dto;

import com.sun.istack.NotNull;
import lombok.*;

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