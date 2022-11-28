package ru.practicum.explorewithme.mapper;

import ru.practicum.explorewithme.model.EndpointHit;
import ru.practicum.explorewithme.model.ViewStats;

import java.util.List;
import java.util.stream.Collectors;

public class ViewStatsMapper {
    private ViewStatsMapper() {
    }

    public static ViewStats toViewStats(EndpointHit endpointHit) {
        return ViewStats.builder()
                .app(endpointHit.getApp())
                .uri(endpointHit.getUri())
                .hits(endpointHit.getHits())
                .build();
    }

    public static List<ViewStats> viewStatsList(List<EndpointHit> endpointHitList) {
        return endpointHitList.stream()
                .map(ViewStatsMapper::toViewStats)
                .collect(Collectors.toList());
    }
}
