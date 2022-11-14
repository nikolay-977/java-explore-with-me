package ru.practicum.explorewithme.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.model.EndpointHit;
import ru.practicum.explorewithme.model.ViewStats;
import ru.practicum.explorewithme.service.StatsService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
@Slf4j
@RequiredArgsConstructor
public class StatsController {

    private final StatsService statsService;

    @PostMapping(path = "/hit")
    public EndpointHit saveEndpointHit(@RequestBody EndpointHit endpointHit) {
        log.info("Send request POST: save endpointHit={}", endpointHit);
        return statsService.saveEndpointHit(endpointHit);

    }

    @GetMapping(path = "/stats")
    public List<ViewStats> getViewStats(@RequestParam(name = "start") String start,
                                        @RequestParam(name = "end") String end,
                                        @RequestParam(name = "uris", required = false) Optional<List<String>> uris,
                                        @RequestParam(name = "unique", defaultValue = "false") Boolean unique) {
        log.info("Send request GET: get view={}");
        return statsService.getViewStats(start, end, uris, unique);
    }
}
