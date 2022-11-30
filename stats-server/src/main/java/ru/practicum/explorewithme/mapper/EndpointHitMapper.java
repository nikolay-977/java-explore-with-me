package ru.practicum.explorewithme.mapper;

import ru.practicum.explorewithme.model.EndpointHit;
import ru.practicum.explorewithme.model.dto.EndpointHitDto;

public class EndpointHitMapper {
    private EndpointHitMapper() {
    }

    public static EndpointHit toEndpointHit(EndpointHitDto endpointHitDto) {
        return EndpointHit.builder()
                .id(endpointHitDto.getId())
                .ip(endpointHitDto.getIp())
                .app(endpointHitDto.getApp())
                .uri(endpointHitDto.getUri())
                .timestamp(endpointHitDto.getTimestamp())
                .build();
    }

    public static EndpointHitDto toEndpointHitDto(EndpointHit endpointHit) {
        return EndpointHitDto.builder()
                .id(endpointHit.getId())
                .ip(endpointHit.getIp())
                .app(endpointHit.getApp())
                .uri(endpointHit.getUri())
                .timestamp(endpointHit.getTimestamp())
                .build();
    }
}