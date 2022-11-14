package ru.practicum.explorewithme.mapper;

import ru.practicum.explorewithme.model.Category;
import ru.practicum.explorewithme.model.Event;
import ru.practicum.explorewithme.model.Location;
import ru.practicum.explorewithme.model.User;
import ru.practicum.explorewithme.model.dto.EventFullDto;
import ru.practicum.explorewithme.model.dto.EventShortDto;
import ru.practicum.explorewithme.model.dto.NewEventDto;

import java.util.List;
import java.util.stream.Collectors;

public class EventMapper {

    public static Event toEvent(EventFullDto eventFullDto) {
        return Event.builder()
                .id(eventFullDto.getId())
                .views(eventFullDto.getViews())
                .eventDate(eventFullDto.getEventDate())
                .initiator(UserMapper.toUser(eventFullDto.getInitiator()))
                .paid(eventFullDto.getPaid())
                .location(LocationMapper.toLocation(eventFullDto.getLocation()))
                .annotation(eventFullDto.getAnnotation())
                .category(CategoryMapper.toCategory(eventFullDto.getCategory()))
                .confirmedRequests(eventFullDto.getConfirmedRequests())
                .state(eventFullDto.getState())
                .title(eventFullDto.getTitle())
                .createdOn(eventFullDto.getCreatedOn())
                .description(eventFullDto.getDescription())
                .participantLimit(eventFullDto.getParticipantLimit())
                .publishedOn(eventFullDto.getPublishedOn())
                .requestModeration(eventFullDto.getRequestModeration())
                .build();
    }

    public static Event toEvent(NewEventDto newEventDto, Category category, User initiator, Location location) {
        return Event.builder()
                .initiator(initiator)
                .eventDate(newEventDto.getEventDate())
                .paid(newEventDto.getPaid())
                .location(location)
                .annotation(newEventDto.getAnnotation())
                .category(category)
                .title(newEventDto.getTitle())
                .description(newEventDto.getDescription())
                .participantLimit(newEventDto.getParticipantLimit())
                .requestModeration(newEventDto.getRequestModeration())
                .build();
    }

    public static EventFullDto toEventFullDto(Event event) {
        return EventFullDto.builder()
                .id(event.getId())
                .views(event.getViews())
                .eventDate(event.getEventDate())
                .initiator(UserMapper.toUserDto(event.getInitiator()))
                .paid(event.getPaid())
                .location(LocationMapper.toLocationDto(event.getLocation()))
                .annotation(event.getAnnotation())
                .category(CategoryMapper.toCategoryDto(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequests())
                .state(event.getState())
                .title(event.getTitle())
                .createdOn(event.getCreatedOn())
                .description(event.getDescription())
                .participantLimit(event.getParticipantLimit())
                .publishedOn(event.getPublishedOn())
                .requestModeration(event.getRequestModeration())
                .build();
    }

    public static EventShortDto toEventShortDto(Event event) {
        return EventShortDto.builder()
                .id(event.getId())
                .views(event.getViews())
                .eventDate(event.getEventDate())
                .paid(event.getPaid())
                .annotation(event.getAnnotation())
                .confirmedRequests(event.getConfirmedRequests())
                .title(event.getTitle())
                .categoryDto(CategoryMapper.toCategoryDto(event.getCategory()))
                .initiatorDto(UserMapper.toUserDto(event.getInitiator()))
                .build();
    }

    public static List<EventShortDto> toEventShortDtoList(List<Event> eventList) {
        return eventList.stream().map(EventMapper::toEventShortDto).collect(Collectors.toList());
    }
}
