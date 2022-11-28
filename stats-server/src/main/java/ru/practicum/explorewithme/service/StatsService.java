package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.model.EndpointHit;
import ru.practicum.explorewithme.model.ViewStats;

import java.util.List;
import java.util.Optional;

public interface StatsService {
    EndpointHit saveEndpointHit(EndpointHit endpointHit);

    List<ViewStats> getViewStats(String start, String end, Optional<List<String>> uris, Boolean unique);
}
