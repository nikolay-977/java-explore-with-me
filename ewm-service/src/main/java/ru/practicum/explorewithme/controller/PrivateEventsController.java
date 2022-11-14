package ru.practicum.explorewithme.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.model.dto.*;
import ru.practicum.explorewithme.service.PrivateEventsService;

import java.util.List;

@RestController
@RequestMapping(path = "/users/{userId}/events")
@Slf4j
@RequiredArgsConstructor
public class PrivateEventsController {

    private final PrivateEventsService privateEventsService;

    @GetMapping
    public List<EventShortDto> getUserEvents(@PathVariable Long userId,
                                             @RequestParam(name = "from", defaultValue = "0") Integer from,
                                             @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Send request GET: get events with userId={}, from={}, size={}",
                userId, from, size);
        return privateEventsService.getUserEvents(userId, from, size);
    }


    @PatchMapping
    public EventFullDto updateUserEvent(@PathVariable Long userId,
                                        @Validated @RequestBody UpdateEventRequest updateEventRequest) {
        log.info("Send request PATCH: update event with userId={}, updateEventRequest={}",
                userId, updateEventRequest);
        return privateEventsService.updateUserEvent(userId, updateEventRequest);
    }

    @PostMapping
    public EventFullDto addUserEvent(@PathVariable Long userId,
                                     @Validated @RequestBody NewEventDto newEventDto) {
        log.info("Send request POST: add event with userId={}, newEventDto {}", userId, newEventDto);
        return privateEventsService.addUserEvent(userId, newEventDto);
    }

    @GetMapping("/{eventId}")
    public EventFullDto getUserEventById(@PathVariable Long userId,
                                         @PathVariable Long eventId) {
        log.info("Send request GET: get user event by userId={}, eventId={}", userId, eventId);
        return privateEventsService.getUserEventById(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto cancelUserEvent(@PathVariable Long userId,
                                        @PathVariable Long eventId) {
        log.info("Send request PATCH: сancel event by userId={}, eventId={}", userId, eventId);
        return privateEventsService.cancelUserEvent(userId, eventId);
    }

    @GetMapping("/{eventId}/requests")
    public List<ParticipationRequestDto> getUserEventRequestsById(@PathVariable Long userId,
                                                                  @PathVariable Long eventId) {
        log.info("Send request GET: get user event requests by userId={}, eventId={}",
                userId, eventId);
        return privateEventsService.getUserEventRequestsById(userId, eventId);
    }

    @PatchMapping("/{eventId}/requests/{reqId}/confirm")
    public ParticipationRequestDto userEventRequestConfirm(@PathVariable Long userId,
                                                           @PathVariable Long eventId,
                                                           @PathVariable Long reqId) {
        log.info("Send request PATCH: confirm request userId={}, eventId={}, reqId={}",
                userId, eventId, reqId);
        return privateEventsService.userEventRequestConfirm(userId, eventId, reqId);
    }

    @PatchMapping("/{eventId}/requests/{reqId}/reject")
    public ParticipationRequestDto userEventRequestReject(@PathVariable Long userId,
                                                          @PathVariable Long eventId,
                                                          @PathVariable Long reqId) {
        log.info("Send request PATCH: reject request userId={}, eventId={}, reqId={}",
                userId, eventId, reqId);
        return privateEventsService.userEventRequestReject(userId, eventId, reqId);
    }
}
