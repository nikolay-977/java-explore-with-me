package ru.practicum.explorewithme.service;


import ru.practicum.explorewithme.model.dto.*;

import java.util.List;

public interface PrivateEventsService {
    List<EventShortDto> getUserEvents(Long userId, Integer from, Integer size);

    EventFullDto updateUserEvent(Long userId, UpdateEventRequest updateEventRequest);

    EventFullDto addUserEvent(Long userId, NewEventDto newEventDto);

    EventFullDto getUserEventById(Long userId, Long eventId);

    EventFullDto cancelUserEvent(Long userId, Long eventId);

    List<ParticipationRequestDto> getUserEventRequestsById(Long userId, Long eventId);

    ParticipationRequestDto userEventRequestConfirm(Long userId, Long eventId, Long reqId);

    ParticipationRequestDto userEventRequestReject(Long userId, Long eventId, Long reqId);
}
