package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.model.dto.AdminUpdateEventRequest;
import ru.practicum.explorewithme.model.dto.EventFullDto;

import java.util.List;

public interface AdminEventsService {
    List<EventFullDto> getEvents(List<Long> users,
                                 List<String> states,
                                 List<Long> categories,
                                 String rangeStart,
                                 String rangeEnd,
                                 Integer from, Integer size);

    EventFullDto updateEvent(Long eventId, AdminUpdateEventRequest adminUpdateEventRequest);

    EventFullDto publishEvent(Long eventId);

    EventFullDto rejectEvent(Long eventId);
}
