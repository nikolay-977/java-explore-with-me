package ru.practicum.explorewithme.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.exception.ForbiddenException;
import ru.practicum.explorewithme.exception.NotFoundException;
import ru.practicum.explorewithme.mapper.EventMapper;
import ru.practicum.explorewithme.mapper.LocationMapper;
import ru.practicum.explorewithme.model.Category;
import ru.practicum.explorewithme.model.Event;
import ru.practicum.explorewithme.model.State;
import ru.practicum.explorewithme.model.dto.AdminUpdateEventRequest;
import ru.practicum.explorewithme.model.dto.EventFullDto;
import ru.practicum.explorewithme.repository.CategoriesRepository;
import ru.practicum.explorewithme.repository.EventsRepository;
import ru.practicum.explorewithme.service.AdminEventsService;
import ru.practicum.explorewithme.utils.CustomPageRequest;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.explorewithme.model.State.CANCELED;
import static ru.practicum.explorewithme.model.State.PENDING;
import static ru.practicum.explorewithme.utils.Constants.*;
import static ru.practicum.explorewithme.utils.DateHelper.parseDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminEventsServiceImpl implements AdminEventsService {

    private final EventsRepository eventsRepository;
    private final CategoriesRepository categoriesRepository;

    @Transactional(readOnly = true)
    @Override
    public List<EventFullDto> getEvents(List<Long> users,
                                        List<String> states,
                                        List<Long> categories,
                                        String rangeStart,
                                        String rangeEnd,
                                        Integer from,
                                        Integer size) {
        List<Event> eventsList;
        List<State> stateList;
        final Pageable pageable = CustomPageRequest.of(from, size);

        if (states == null && rangeStart == null && rangeEnd == null) {
            eventsList = eventsRepository
                    .searchEventsByInitiatorsAndCategories(
                            users,
                            categories,
                            pageable);
        } else {
            stateList = states.stream()
                    .map(State::valueOf)
                    .collect(Collectors.toList());

            eventsList = eventsRepository
                    .searchEventsByAdminAndStatesAndCategoriesAndRange(
                            users,
                            stateList,
                            categories,
                            parseDateTime(rangeStart),
                            parseDateTime(rangeEnd), pageable);
        }

        if (eventsList.isEmpty()) {
            throw new NotFoundException(EVENTS_NOT_FOUND_MESSAGE);
        }

        List<EventFullDto> eventFullDtoList = eventsList.stream().map(EventMapper::toEventFullDto).collect(Collectors.toList());
        log.info("Got events, {}", eventFullDtoList);
        return eventFullDtoList;
    }

    @Transactional
    @Override
    public EventFullDto updateEvent(Long eventId, AdminUpdateEventRequest adminUpdateEventRequest) {
        Event event = findEvent(eventId);
        if (adminUpdateEventRequest.getAnnotation() != null) {
            event.setAnnotation(adminUpdateEventRequest.getAnnotation());
        }
        if (adminUpdateEventRequest.getParticipantLimit() != null) {
            event.setParticipantLimit(adminUpdateEventRequest.getParticipantLimit());
        }
        if (adminUpdateEventRequest.getRequestModeration() != null) {
            event.setRequestModeration(adminUpdateEventRequest.getRequestModeration());
        }
        if (adminUpdateEventRequest.getEventDate() != null) {
            event.setEventDate(parseDateTime(adminUpdateEventRequest.getEventDate()));
        }
        if (adminUpdateEventRequest.getLocationDto() != null) {
            event.setLocation(LocationMapper.toLocation(adminUpdateEventRequest.getLocationDto()));
        }
        if (adminUpdateEventRequest.getPaid() != null) {
            event.setPaid(adminUpdateEventRequest.getPaid());
        }
        if (adminUpdateEventRequest.getCategoryId() != null) {
            event.setCategory(findCategory(adminUpdateEventRequest.getCategoryId()));
        }
        if (adminUpdateEventRequest.getDescription() != null) {
            event.setDescription(adminUpdateEventRequest.getDescription());
        }
        if (adminUpdateEventRequest.getTitle() != null) {
            event.setTitle(adminUpdateEventRequest.getTitle());
        }
        EventFullDto eventFullDto = EventMapper.toEventFullDto(eventsRepository.save(event));
        log.info("Updated event, id={}", eventFullDto.getId());
        return eventFullDto;
    }

    @Transactional
    @Override
    public EventFullDto publishEvent(Long eventId) {
        Event event = findEvent(eventId);
        if (event.getState() != PENDING) {
            throw new ForbiddenException(IS_NOT_PENDING);
        }
        event.setPublishedOn(LocalDateTime.now());
        event.setState(State.PUBLISHED);
        EventFullDto eventFullDto = EventMapper.toEventFullDto(eventsRepository.save(event));
        log.info("Published event, id={}", eventFullDto.getId());
        return eventFullDto;
    }

    @Transactional
    @Override
    public EventFullDto rejectEvent(Long eventId) {
        Event event = findEvent(eventId);
        if (event.getState() != PENDING) {
            throw new ForbiddenException(IS_NOT_PENDING);
        }
        event.setState(CANCELED);
        EventFullDto eventFullDto = EventMapper.toEventFullDto(eventsRepository.save(event));
        log.info("Rejected event, id={}", eventFullDto.getId());
        return eventFullDto;
    }

    private Event findEvent(Long id) {
        return eventsRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(MessageFormat.format("{0}{1}", COMPILATION_NOT_FOUND_MESSAGE, id)));
    }

    private Category findCategory(Long id) {
        return categoriesRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(MessageFormat.format("{0}{1}", CATEGORY_NOT_FOUND_MESSAGE, id)));
    }
}
