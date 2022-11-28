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
        final Pageable pageable = CustomPageRequest.of(from, size);

        eventsList = (states == null && rangeStart == null && rangeEnd == null)
                ? eventsRepository.searchEventsByInitiatorsAndCategories(users, categories, pageable)
                : eventsRepository.searchEventsByAdminAndStatesAndCategoriesAndRange(
                users,
                states.stream()
                        .map(State::valueOf)
                        .collect(Collectors.toList()),
                categories,
                parseDateTime(rangeStart),
                parseDateTime(rangeEnd),
                pageable);

        if (eventsList.isEmpty()) {
            throw new NotFoundException(EVENTS_NOT_FOUND_MESSAGE);
        }

        List<EventFullDto> eventFullDtoList = eventsList.stream()
                .map(EventMapper::toEventFullDto).collect(Collectors.toList());
        log.info("Got events, {}", eventFullDtoList);
        return eventFullDtoList;
    }

    @Transactional
    @Override
    public EventFullDto updateEvent(Long eventId, AdminUpdateEventRequest adminUpdateEventRequest) {
        Event event = findEvent(eventId);

        adminUpdateEventRequest.getAnnotation().ifPresent(event::setAnnotation);
        adminUpdateEventRequest.getParticipantLimit().ifPresent(event::setParticipantLimit);
        adminUpdateEventRequest.getEventDate().ifPresent(s -> event.setEventDate(parseDateTime(s)));
        adminUpdateEventRequest.getPaid().ifPresent(event::setPaid);
        adminUpdateEventRequest.getCategoryId().ifPresent(s -> event.setCategory(findCategory(s)));
        adminUpdateEventRequest.getDescription().ifPresent(event::setDescription);
        adminUpdateEventRequest.getTitle().ifPresent(event::setTitle);

        if (adminUpdateEventRequest.getRequestModeration() != null) {
            event.setRequestModeration(adminUpdateEventRequest.getRequestModeration());
        }

        if (adminUpdateEventRequest.getLocationDto() != null) {
            event.setLocation(LocationMapper.toLocation(adminUpdateEventRequest.getLocationDto()));
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
                .orElseThrow(() -> new NotFoundException(
                        MessageFormat.format(PATTERN_TWO_ARGS, COMPILATION_NOT_FOUND_MESSAGE, id)));
    }

    private Category findCategory(Long id) {
        return categoriesRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(
                        MessageFormat.format(PATTERN_TWO_ARGS, CATEGORY_NOT_FOUND_MESSAGE, id)));
    }
}
