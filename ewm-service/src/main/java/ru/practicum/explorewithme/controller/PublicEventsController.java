package ru.practicum.explorewithme.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.model.dto.EventFullDto;
import ru.practicum.explorewithme.model.dto.EventShortDto;
import ru.practicum.explorewithme.service.PublicEventsService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/events")
@Slf4j
@RequiredArgsConstructor
public class PublicEventsController {
    private final PublicEventsService publicEventsService;

    @GetMapping
    public List<EventShortDto> getEvents(@RequestParam(name = "text", required = false) Optional<String> text,
                                         @RequestParam(name = "categories", required = false) Optional<List<Long>> categories,
                                         @RequestParam(name = "paid", required = false) Optional<Boolean> paid,
                                         @RequestParam(name = "rangeStart", required = false) Optional<String> rangeStart,
                                         @RequestParam(name = "rangeEnd", required = false) Optional<String> rangeEnd,
                                         @RequestParam(name = "onlyAvailable", required = false) Optional<Boolean> onlyAvailable,
                                         @RequestParam(name = "sort", defaultValue = "VIEWS") String sort,
                                         @RequestParam(name = "from", defaultValue = "0") Integer from,
                                         @RequestParam(name = "size", defaultValue = "10") Integer size,
                                         HttpServletRequest request) {
        log.info("Send request GET: get events by text={}, rangeStart={}, rangeEnd={}, from={}, size={}",
                text, rangeStart, rangeEnd, from, size);
        return publicEventsService.getEvents(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort,
                from, size, request);
    }

    @GetMapping("/{id}")
    public EventFullDto getEvent(@PathVariable Long id, HttpServletRequest request) {
        log.info("Send request GET: get event by id={}", id);
        return publicEventsService.getEventById(id, request);
    }
}
