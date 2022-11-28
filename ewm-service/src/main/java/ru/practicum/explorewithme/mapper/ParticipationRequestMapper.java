package ru.practicum.explorewithme.mapper;

import ru.practicum.explorewithme.model.ParticipationRequest;
import ru.practicum.explorewithme.model.dto.ParticipationRequestDto;

import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.explorewithme.utils.DateHelper.FORMATTER;

public class ParticipationRequestMapper {

    private ParticipationRequestMapper() {
    }

    public static ParticipationRequestDto toParticipationRequestDto(ParticipationRequest participationRequest) {
        return ParticipationRequestDto.builder()
                .id(participationRequest.getId())
                .created(participationRequest.getCreated() != null
                        ? participationRequest.getCreated().format(FORMATTER)
                        : null)
                .event(participationRequest.getEvent().getId())
                .status(participationRequest.getStatus().toString())
                .requesterId(participationRequest.getRequester().getId())
                .build();
    }

    public static List<ParticipationRequestDto> toParticipationRequestDtoList(
            List<ParticipationRequest> participationRequestList) {
        return participationRequestList.stream()
                .map(ParticipationRequestMapper::toParticipationRequestDto).collect(Collectors.toList());
    }
}
