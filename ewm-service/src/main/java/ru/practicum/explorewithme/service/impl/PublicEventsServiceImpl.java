package ru.practicum.explorewithme.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.client.StatsClient;
import ru.practicum.explorewithme.exception.NotFoundException;
import ru.practicum.explorewithme.mapper.EventMapper;
import ru.practicum.explorewithme.model.Event;
import ru.practicum.explorewithme.model.dto.EndpointHitDto;
import ru.practicum.explorewithme.model.dto.EventFullDto;
import ru.practicum.explorewithme.model.dto.EventShortDto;
import ru.practicum.explorewithme.repository.EventsRepository;
import ru.practicum.explorewithme.service.PublicEventsService;
import ru.practicum.explorewithme.utils.CustomPageRequest;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.explorewithme.utils.Constants.COMPILATION_NOT_FOUND_MESSAGE;
import static ru.practicum.explorewithme.utils.DateHelper.parseDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class PublicEventsServiceImpl implements PublicEventsService {

    private final EventsRepository eventsRepository;

    private final StatsClient statsClient;

    @Transactional(readOnly = true)
    @Override
    public List<EventShortDto> getEvents(String text,
                                         List<Long> categories,
                                         Boolean paid,
                                         String rangeStart,
                                         String rangeEnd,
                                         Boolean onlyAvailable,
                                         String sort,
                                         Integer from,
                                         Integer size,
                                         HttpServletRequest request) {
        List<Event> eventList = new ArrayList<>();
        final Pageable pageable = CustomPageRequest.of(from, size);

        switch (sort) {
            case "EVENT_DATE": {
                if (text != null && categories != null && paid != null && rangeStart != null && rangeEnd != null) {
                    eventList = eventsRepository
                            .searchEventsByTextAndCategoriesAndPaidBySortByEventDay(text, categories, paid, parseDateTime(rangeStart), parseDateTime(rangeEnd), pageable);
                } else {
                    eventList = eventsRepository
                            .searchEventsByTextSortByEvents(text, pageable);
                }
                break;
            }
            case "VIEWS": {
                if (text != null && categories != null && paid != null && rangeStart != null && rangeEnd != null) {
                    eventList = eventsRepository
                            .searchEventsByTextAndCategoriesAndPaidBySortByViews(text, categories, paid, parseDateTime(rangeStart), parseDateTime(rangeEnd), pageable);
                } else {
                    eventList = eventsRepository
                            .searchEventsByTextSortByViews(text, pageable);
                }
                break;
            }
        }

        if (paid != null) {
            eventList = eventList
                    .stream()
                    .filter(event -> event.getPaid().equals(paid))
                    .collect(Collectors.toList());
        }
        if (onlyAvailable != null) {
            if (onlyAvailable) {
                eventList = eventList
                        .stream()
                        .filter(event -> event.getParticipantLimit() > 0L)
                        .collect(Collectors.toList());
            }
        }

        List<EventShortDto> eventShortDtoList = EventMapper.toEventShortDtoList(eventList);
        log.info("Got events={}", eventShortDtoList);
        EndpointHitDto endpointHit = new EndpointHitDto();
        endpointHit.setUri(request.getRequestURI());
        endpointHit.setIp(request.getRemoteAddr());
        endpointHit.setTimestamp(LocalDateTime.now());
        statsClient.sendStatistics(endpointHit);
        return eventShortDtoList;
    }

    @Transactional(readOnly = true)
    @Override
    public EventFullDto getEventById(Long id, HttpServletRequest request) {
        Event event = findEvent(id);
        EventFullDto eventFullDto = EventMapper.toEventFullDto(event);
        log.info("Got event={}", eventFullDto);
        EndpointHitDto endpointHit = new EndpointHitDto();
        endpointHit.setUri(request.getRequestURI());
        endpointHit.setIp(request.getRemoteAddr());
        endpointHit.setTimestamp(LocalDateTime.now());
        statsClient.sendStatistics(endpointHit);
        return eventFullDto;
    }

    private Event findEvent(Long id) {
        return eventsRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(MessageFormat.format("{0}{1}", COMPILATION_NOT_FOUND_MESSAGE, id)));
    }
}
