package ru.practicum.explorewithme.service;


import ru.practicum.explorewithme.model.dto.EventFullDto;
import ru.practicum.explorewithme.model.dto.EventShortDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface PublicEventsService {

    List<EventShortDto> getEvents(String text, List<Long> categories, Boolean paid, String rangeStart, String rangeEnd, Boolean onlyAvailable, String sort, Integer from, Integer size, HttpServletRequest request);

    EventFullDto getEventById(Long id, HttpServletRequest request);
}
