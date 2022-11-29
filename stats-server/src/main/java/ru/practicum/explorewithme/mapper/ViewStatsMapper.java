package ru.practicum.explorewithme.mapper;

import ru.practicum.explorewithme.model.ViewStats;
import ru.practicum.explorewithme.model.dto.ViewStatsDto;

import java.util.List;
import java.util.stream.Collectors;

public class ViewStatsMapper {
    private ViewStatsMapper() {
    }

    public static ViewStatsDto toViewStatsDto(ViewStats viewStats) {
        return ViewStatsDto.builder()
                .app(viewStats.getApp())
                .uri(viewStats.getUri())
                .hits(viewStats.getHits())
                .build();
    }

    public static List<ViewStatsDto> toViewStatsDtoList(List<ViewStats> viewStatsList) {
        return viewStatsList.stream()
                .map(ViewStatsMapper::toViewStatsDto)
                .collect(Collectors.toList());
    }
}
