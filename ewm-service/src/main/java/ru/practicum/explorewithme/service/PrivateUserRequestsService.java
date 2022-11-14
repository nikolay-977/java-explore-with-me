package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.model.dto.ParticipationRequestDto;

import java.util.List;

public interface PrivateUserRequestsService {
    List<ParticipationRequestDto> getUserRequests(Long userId);

    ParticipationRequestDto addUserRequest(Long userId, Long eventId);

    ParticipationRequestDto cancelUserRequest(Long userId, Long requestId);
}
