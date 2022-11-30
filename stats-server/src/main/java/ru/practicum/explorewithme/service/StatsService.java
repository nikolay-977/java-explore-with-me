package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.model.dto.EndpointHitDto;
import ru.practicum.explorewithme.model.dto.ViewStatsDto;

import java.util.List;

public interface StatsService {
    EndpointHitDto saveEndpointHit(EndpointHitDto endpointHitDto);

    List<ViewStatsDto> getViewStats(String start, String end, List<String> uris, Boolean unique);
}