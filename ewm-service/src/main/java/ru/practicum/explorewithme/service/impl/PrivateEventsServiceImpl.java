package ru.practicum.explorewithme.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.exception.BadRequestException;
import ru.practicum.explorewithme.exception.ForbiddenException;
import ru.practicum.explorewithme.exception.NotFoundException;
import ru.practicum.explorewithme.mapper.EventMapper;
import ru.practicum.explorewithme.mapper.LocationMapper;
import ru.practicum.explorewithme.mapper.ParticipationRequestMapper;
import ru.practicum.explorewithme.model.*;
import ru.practicum.explorewithme.model.dto.*;
import ru.practicum.explorewithme.repository.*;
import ru.practicum.explorewithme.service.PrivateEventsService;
import ru.practicum.explorewithme.utils.CustomPageRequest;
import ru.practicum.explorewithme.utils.DateHelper;

import java.text.MessageFormat;
import java.util.List;

import static ru.practicum.explorewithme.model.State.*;
import static ru.practicum.explorewithme.utils.Constants.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrivateEventsServiceImpl implements PrivateEventsService {

    private final EventsRepository eventsRepository;
    private final UsersRepository usersRepository;
    private final CategoriesRepository categoriesRepository;
    private final LocationsRepository locationsRepository;
    private final RequestsRepository requestsRepository;

    @Transactional(readOnly = true)
    @Override
    public List<EventShortDto> getUserEvents(Long userId, Integer from, Integer size) {
        final Pageable pageable = CustomPageRequest.of(from, size);
        List<Event> eventList = eventsRepository.findEventsByInitiatorId(userId, pageable);
        List<EventShortDto> eventShortDtoList = EventMapper.toEventShortDtoList(eventList);
        log.info("Got events={} by userId={}", eventShortDtoList, userId);
        return eventShortDtoList;
    }

    @Transactional
    @Override
    public EventFullDto updateUserEvent(Long userId, UpdateEventRequest updateEventRequest) {
        findUser(userId);

        Event event = findEvent(updateEventRequest.getEventId());
        if (!event.getInitiator().getId().equals(userId)) {
            throw new ForbiddenException(USER_IS_INITIATOR);
        }
        if (event.getState().equals(PUBLISHED)) {
            throw new BadRequestException(IS_PUBLISHED);
        }
        if (event.getState().equals(CANCELED)) {
            event.setRequestModeration(true);
        }

        updateEventRequest.getCategoryId().ifPresent(s -> event.setCategory(findCategory(s)));
        updateEventRequest.getEventDate().ifPresent(s -> event.setEventDate(DateHelper.parseDateTime(s)));
        updateEventRequest.getAnnotation().ifPresent(event::setAnnotation);
        updateEventRequest.getDescription().ifPresent(event::setDescription);
        updateEventRequest.getPaid().ifPresent(event::setPaid);
        updateEventRequest.getParticipantLimit().ifPresent(event::setParticipantLimit);
        updateEventRequest.getTitle().ifPresent(event::setTitle);
        event.setState(PENDING);

        EventFullDto eventFullDto = EventMapper.toEventFullDto(eventsRepository.save(event));
        log.info("Event id={} edited by user id={}", eventFullDto.getId(), userId);
        return eventFullDto;
    }

    @Transactional
    @Override
    public EventFullDto addUserEvent(Long userId, NewEventDto newEventDto) {
        User user = findUser(userId);
        Category category = findCategory(newEventDto.getCategoryId());
        Location location = locationsRepository.save(LocationMapper.toLocation(newEventDto.getLocationDto()));
        Event event = EventMapper.toEvent(newEventDto, category, user, location);
        EventFullDto eventFullDto = EventMapper.toEventFullDto(eventsRepository.save(event));
        log.info("Added new event, id={}", eventFullDto.getId());
        return eventFullDto;
    }

    @Transactional(readOnly = true)
    @Override
    public EventFullDto getUserEventById(Long userId, Long eventId) {
        findUser(userId);
        EventFullDto eventFullDto = EventMapper.toEventFullDto(findEvent(eventId));
        log.info("Got event, id={}", eventId);
        return eventFullDto;
    }

    @Transactional
    @Override
    public EventFullDto cancelUserEvent(Long userId, Long eventId) {
        findUser(userId);
        Event event = findEvent(eventId);
        event.setState(CANCELED);
        EventFullDto eventFullDto = EventMapper.toEventFullDto(eventsRepository.save(event));
        log.info("Canceled event, id={}", eventId);
        return eventFullDto;
    }

    @Transactional(readOnly = true)
    @Override
    public List<ParticipationRequestDto> getUserEventRequestsById(Long userId, Long eventId) {
        List<ParticipationRequest> participationRequestList = requestsRepository
                .searchRequestsByUserIdAndEventId(eventId, userId);
        List<ParticipationRequestDto> participationRequestDtoList = ParticipationRequestMapper
                .toParticipationRequestDtoList(participationRequestList);
        log.info("Got participation request list=", participationRequestDtoList);
        return participationRequestDtoList;
    }

    @Transactional
    @Override
    public ParticipationRequestDto userEventRequestConfirm(Long userId, Long eventId, Long reqId) {
        findUser(userId);
        Event event = findEvent(eventId);
        ParticipationRequest participationRequest = findRequest(reqId);
        eventsRepository.save(event);
        participationRequest.setStatus(CONFIRMED);
        ParticipationRequestDto participationRequestDto = ParticipationRequestMapper.toParticipationRequestDto(requestsRepository.save(participationRequest));
        log.info("Confirmed request, id={}", reqId);
        return participationRequestDto;
    }

    @Transactional
    @Override
    public ParticipationRequestDto userEventRequestReject(Long userId, Long eventId, Long reqId) {
        findUser(userId);
        Event event = findEvent(eventId);
        ParticipationRequest participationRequest = findRequest(reqId);
        eventsRepository.save(event);
        participationRequest.setStatus(REJECTED);
        ParticipationRequestDto participationRequestDto = ParticipationRequestMapper.toParticipationRequestDto(requestsRepository.save(participationRequest));
        log.info("Rejected request, id={}", reqId);
        return participationRequestDto;
    }

    private Event findEvent(Long id) {
        return eventsRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(MessageFormat.format(PATTERN_TWO_ARGS, COMPILATION_NOT_FOUND_MESSAGE, id)));
    }

    private User findUser(Long id) {
        return usersRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(MessageFormat.format(PATTERN_TWO_ARGS, USER_NOT_FOUND_MESSAGE, id)));
    }

    private Category findCategory(Long id) {
        return categoriesRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(MessageFormat.format(PATTERN_TWO_ARGS, CATEGORY_NOT_FOUND_MESSAGE, id)));
    }

    private ParticipationRequest findRequest(Long id) {
        return requestsRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(MessageFormat.format(PATTERN_TWO_ARGS, REQUEST_NOT_FOUND_MESSAGE, id)));
    }
}
