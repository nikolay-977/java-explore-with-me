package ru.practicum.explorewithme.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.exception.NotFoundException;
import ru.practicum.explorewithme.mapper.ParticipationRequestMapper;
import ru.practicum.explorewithme.model.Event;
import ru.practicum.explorewithme.model.ParticipationRequest;
import ru.practicum.explorewithme.model.User;
import ru.practicum.explorewithme.model.dto.ParticipationRequestDto;
import ru.practicum.explorewithme.repository.EventsRepository;
import ru.practicum.explorewithme.repository.RequestsRepository;
import ru.practicum.explorewithme.repository.UsersRepository;
import ru.practicum.explorewithme.service.PrivateUserRequestsService;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.explorewithme.model.State.CANCELED;
import static ru.practicum.explorewithme.model.State.CONFIRMED;
import static ru.practicum.explorewithme.utils.Constants.COMPILATION_NOT_FOUND_MESSAGE;
import static ru.practicum.explorewithme.utils.Constants.USER_NOT_FOUND_MESSAGE;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrivateUserRequestsServiceImpl implements PrivateUserRequestsService {

    private final UsersRepository usersRepository;
    private final EventsRepository eventsRepository;

    private final RequestsRepository requestsRepository;

    @Override
    public List<ParticipationRequestDto> getUserRequests(Long userId) {
        List<ParticipationRequest> requestList = requestsRepository.findAllByRequesterId(userId);
        List<ParticipationRequestDto> participationRequestDtoList = ParticipationRequestMapper.toParticipationRequestDtoList(requestList);
        log.info("Got requests={}", participationRequestDtoList);
        return participationRequestDtoList;
    }

    @Transactional

    @Override
    public ParticipationRequestDto addUserRequest(Long userId, Long eventId) {
        User user = findUser(userId);
        Event event = findEvent(eventId);

        ParticipationRequest participationRequest = ParticipationRequest.builder()
                .requester(user)
                .created(LocalDateTime.now())
                .event(event)
                .build();

        ParticipationRequestDto participationRequestDto = ParticipationRequestMapper.toParticipationRequestDto(requestsRepository.save(participationRequest));
        log.info("Added request={}", participationRequestDto);
        return participationRequestDto;
    }

    @Transactional
    @Override
    public ParticipationRequestDto cancelUserRequest(Long userId, Long requestId) {
        ParticipationRequest participationRequest = requestsRepository.findAllByRequesterIdAndIdAndStatusNot(userId, requestId, CONFIRMED);
        participationRequest.setStatus(CANCELED);
        requestsRepository.save(participationRequest);
        ParticipationRequestDto participationRequestDto = ParticipationRequestMapper.toParticipationRequestDto(participationRequest);
        log.info("Canceled request, id={}", requestId);
        return participationRequestDto;
    }

    private Event findEvent(Long id) {
        return eventsRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(MessageFormat.format("{0}{1}", COMPILATION_NOT_FOUND_MESSAGE, id)));
    }

    private User findUser(Long id) {
        return usersRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(MessageFormat.format("{0}{1}", USER_NOT_FOUND_MESSAGE, id)));
    }
}
