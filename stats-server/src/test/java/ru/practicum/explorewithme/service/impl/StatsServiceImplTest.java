package ru.practicum.explorewithme.service.impl;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.explorewithme.mapper.ViewStatsMapper;
import ru.practicum.explorewithme.model.EndpointHit;
import ru.practicum.explorewithme.model.ViewStats;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class StatsServiceImplTest {

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
    @Autowired
    private StatsServiceImpl statsService;
    @Autowired
    private EntityManager entityManager;
    private EndpointHit endpointHitOne;
    private EndpointHit endpointHitTwo;
    private EndpointHit endpointHitThree;

    @BeforeEach
    void setUp() {
        endpointHitOne = EndpointHit.builder()
                .app("ewm-service")
                .ip("192.168.1.1")
                .uri("/events")
                .build();

        endpointHitTwo = EndpointHit.builder()
                .app("ewm-service")
                .ip("192.168.1.2")
                .uri("/events")
                .build();

        endpointHitThree = EndpointHit.builder()
                .app("ewm-service")
                .ip("192.168.1.3")
                .uri("/events")
                .build();
    }

    @Test
    void saveEndpointHit() {
        EndpointHit endpointHitSaved = statsService.saveEndpointHit(endpointHitOne);
        assertEquals(endpointHitOne.getApp(), endpointHitSaved.getApp());
        assertEquals(endpointHitOne.getIp(), endpointHitSaved.getIp());
        assertEquals(endpointHitOne.getUri(), endpointHitSaved.getUri());
        assertEquals(endpointHitOne.getHits(), endpointHitSaved.getHits());
        assertEquals(endpointHitOne.getTimestamp(), endpointHitSaved.getTimestamp());
    }

    @Test
    void getViewStats() {
        entityManager.persist(endpointHitOne);
        entityManager.persist(endpointHitTwo);
        entityManager.persist(endpointHitThree);

        List<ViewStats> expectedViewStatsList = ViewStatsMapper.viewStatsList(List.of(endpointHitOne, endpointHitTwo, endpointHitThree));

        String start = LocalDateTime.now().minusHours(1L).format(FORMATTER);
        String end = LocalDateTime.now().plusHours(1L).format(FORMATTER);

        List<ViewStats> actualViewStatsList = statsService.getViewStats(start, end, Optional.of(List.of("/events")), true);

        assertEquals(expectedViewStatsList, actualViewStatsList);
    }
}