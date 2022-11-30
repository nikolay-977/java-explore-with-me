package ru.practicum.explorewithme.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.mapper.EndpointHitMapper;
import ru.practicum.explorewithme.mapper.ViewStatsMapper;
import ru.practicum.explorewithme.model.EndpointHit;
import ru.practicum.explorewithme.model.ViewStats;
import ru.practicum.explorewithme.model.dto.EndpointHitDto;
import ru.practicum.explorewithme.model.dto.ViewStatsDto;
import ru.practicum.explorewithme.repository.StatsRepository;
import ru.practicum.explorewithme.service.StatsService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {

    private final StatsRepository statsRepository;

    @Override
    public EndpointHitDto saveEndpointHit(EndpointHitDto endpointHitDto) {
        EndpointHit endpointHit = statsRepository.save(EndpointHitMapper.toEndpointHit(endpointHitDto));
        log.info("Added endpointHit={}", endpointHit);
        return EndpointHitMapper.toEndpointHitDto(endpointHit);
    }

    @Override
    public List<ViewStatsDto> getViewStats(String start, String end, List<String> uris, Boolean unique) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDateTime = LocalDateTime.parse(start, formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(end, formatter);

        List<ViewStats> viewStatsList = (unique)
                ? statsRepository.getUniqueTimestampAndUris(startDateTime, endDateTime, uris)
                : statsRepository.getByTimestampAndUris(startDateTime, endDateTime, uris);

        log.info("Got viewStatsList={}", viewStatsList);
        return ViewStatsMapper.toViewStatsDtoList(viewStatsList);
    }
}