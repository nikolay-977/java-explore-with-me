package ru.practicum.explorewithme.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.model.dto.ParticipationRequestDto;
import ru.practicum.explorewithme.service.PrivateUserRequestsService;

import java.util.List;

@RestController
@RequestMapping(path = "/users/{userId}/requests")
@Slf4j
@RequiredArgsConstructor
public class PrivateRequestsController {
    private final PrivateUserRequestsService privateUserRequestsService;

    @GetMapping
    public List<ParticipationRequestDto> getUserRequests(@PathVariable Long userId) {
        log.info("Send request GET: get user request by userId={}", userId);
        return privateUserRequestsService.getUserRequests(userId);
    }

    @PostMapping
    public ParticipationRequestDto addUserRequest(@PathVariable Long userId,
                                                  @RequestParam Long eventId) {
        log.info("Send request POST: add request userId={}, eventId={}", userId, eventId);
        return privateUserRequestsService.addUserRequest(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    public ParticipationRequestDto updateCancelUserRequest(@PathVariable Long userId,
                                                           @PathVariable Long requestId) {
        log.info("Send request PATCH: cancel user request userId={}, requestId={}",
                userId, requestId);
        return privateUserRequestsService.cancelUserRequest(userId, requestId);
    }
}
