package ru.practicum.explorewithme.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.model.dto.AdminUpdateEventRequest;
import ru.practicum.explorewithme.model.dto.EventFullDto;
import ru.practicum.explorewithme.service.AdminEventsService;

import java.util.List;

@RestController
@RequestMapping(path = "/admin/events")
@Slf4j
@RequiredArgsConstructor
public class AdminEventsController {

    private final AdminEventsService adminEventsService;

    @GetMapping()
    public List<EventFullDto> getEvents(@RequestParam(name = "users", required = false) List<Long> users,
                                        @RequestParam(name = "states", required = false) List<String> states,
                                        @RequestParam(name = "categories", required = false) List<Long> categories,
                                        @RequestParam(name = "rangeStart", required = false) String rangeStart,
                                        @RequestParam(name = "rangeEnd", required = false) String rangeEnd,
                                        @RequestParam(name = "from", defaultValue = "0") Integer from,
                                        @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Send request GET: get events by users={}, rangeStart={}, rangeEnd={}, from={}, size={}",
                users, rangeStart, rangeEnd, from, size);
        return adminEventsService.getEvents(users, states, categories, rangeStart,
                rangeEnd, from, size);
    }

    @PutMapping("/{eventId}")
    public EventFullDto updateEvent(@PathVariable Long eventId,
                                    @RequestBody AdminUpdateEventRequest adminUpdateEventRequest) {
        log.info("Send request PUT: update event by eventId={}, adminUpdateEventRequest={}",
                eventId, adminUpdateEventRequest);
        return adminEventsService.updateEvent(eventId, adminUpdateEventRequest);
    }

    @PatchMapping("/{eventId}/publish")
    public EventFullDto publishEvent(@PathVariable Long eventId) {
        log.info("Send request PATCH: publish event by eventId={}", eventId);
        return adminEventsService.publishEvent(eventId);
    }

    @PatchMapping("/{eventId}/reject")
    public EventFullDto rejectEvent(@PathVariable Long eventId) {
        log.info("Send request PATCH: reject event by eventId={}", eventId);
        return adminEventsService.rejectEvent(eventId);
    }
}
