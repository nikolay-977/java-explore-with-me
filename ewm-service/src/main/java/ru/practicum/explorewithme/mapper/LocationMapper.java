package ru.practicum.explorewithme.mapper;

import ru.practicum.explorewithme.model.Location;
import ru.practicum.explorewithme.model.dto.LocationDto;

public class LocationMapper {
    public static Location toLocation(LocationDto locationDto) {
        return Location.builder()
                .id(locationDto.getId())
                .lat(locationDto.getLat())
                .lon(locationDto.getLon())
                .build();
    }

    public static LocationDto toLocationDto(Location location) {
        return LocationDto.builder()
                .id(location.getId())
                .lat(location.getLat())
                .lon(location.getLon())
                .build();
    }
}
