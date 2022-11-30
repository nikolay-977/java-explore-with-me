package ru.practicum.explorewithme.model.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ViewStatsDto {
    private String app;
    private String uri;
    private Long hits;
}