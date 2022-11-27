package ru.practicum.explorewithme.model;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ViewStats {

    private String app;

    private String uri;

    private Long hits;
}