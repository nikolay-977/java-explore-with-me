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

@Slf4j
@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {

    private final StatsRepository statsRepository;

    @Override
    public EndpointHit saveEndpointHit(EndpointHit endpointHit) {
        EndpointHit endpointHitSaved = statsRepository.save(endpointHit);
        log.info("Added endpointHit={}", endpointHitSaved);
        return endpointHitSaved;
    }

    @Override
    public List<ViewStats> getViewStats(String start, String end, Optional<List<String>> uris, Boolean unique) {
        List<EndpointHit> listEndpoint;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDateTime = LocalDateTime.parse(start, formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(end, formatter);

        if (unique) {
            listEndpoint = uris.isPresent() ?
                    statsRepository
                            .searchUniqueEndpointHitByUri(uris.get(), startDateTime, endDateTime)
                    :
                    statsRepository
                            .searchUniqueEndpointHit(startDateTime, endDateTime);

        } else {
            listEndpoint = uris.isPresent() ?
                    statsRepository
                            .searchEndpointHitByUri(uris.get(), startDateTime, endDateTime)
                    :
                    statsRepository
                            .searchEndpointHit(startDateTime, endDateTime);
        }

        List<ViewStats> viewStatsList = new ArrayList<>();

        if (!listEndpoint.isEmpty()) {
            viewStatsList = ViewStatsMapper.viewStatsList(listEndpoint);
        }
        log.info("Got viewStatsList={}", viewStatsList);
        return viewStatsList;
    }
}
