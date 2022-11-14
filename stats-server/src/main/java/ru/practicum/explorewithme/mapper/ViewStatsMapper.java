package ru.practicum.explorewithme.mapper;

import ru.practicum.explorewithme.model.EndpointHit;
import ru.practicum.explorewithme.model.ViewStats;

public class ViewStatsMapper {

    public static ViewStats toViewStats(EndpointHit endpointHit) {
        return ViewStats.builder()
                .app(endpointHit.getApp())
                .uri(endpointHit.getUri())
                .hits(endpointHit.getHits())
                .build();
    }
}
