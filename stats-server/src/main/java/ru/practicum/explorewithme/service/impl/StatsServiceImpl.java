package ru.practicum.explorewithme.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.mapper.ViewStatsMapper;
import ru.practicum.explorewithme.model.EndpointHit;
import ru.practicum.explorewithme.model.ViewStats;
import ru.practicum.explorewithme.repository.StatsRepository;
import ru.practicum.explorewithme.service.StatsService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {

    private final StatsRepository statsRepository;

    @Override
    public EndpointHit saveEndpointHit(EndpointHit endpointHit) {
        return statsRepository.save(endpointHit);
    }

    @Override
    public List<ViewStats> getViewStats(String start, String end, Optional<List<String>> uris, Boolean unique) {
        List<EndpointHit> listEndpoint;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDateTime = LocalDateTime.parse(start, formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(end, formatter);

        if (unique) {
            if (uris.isPresent()) {
                listEndpoint = statsRepository
                        .searchUniqueEndpointHitByUri(uris.get(), startDateTime, endDateTime);
            } else {
                listEndpoint = statsRepository
                        .searchUniqueEndpointHit(startDateTime, endDateTime);
            }
        } else {
            if (uris.isPresent()) {
                listEndpoint = statsRepository
                        .searchEndpointHitByUri(uris.get(), startDateTime, endDateTime);
            } else {
                listEndpoint = statsRepository
                        .searchEndpointHit(startDateTime, endDateTime);
            }
        }

        List<ViewStats> viewStatsList = new ArrayList<>();

        if (!listEndpoint.isEmpty()) {
            viewStatsList = listEndpoint
                    .stream()
                    .map(ViewStatsMapper::toViewStats)
                    .collect(Collectors.toList());
        }
        return viewStatsList;
    }
}
