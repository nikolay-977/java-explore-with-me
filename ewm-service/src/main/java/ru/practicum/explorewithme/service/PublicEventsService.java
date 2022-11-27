package ru.practicum.explorewithme.service;


import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.model.dto.EventFullDto;
import ru.practicum.explorewithme.model.dto.EventShortDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public interface PublicEventsService {

    @Transactional(readOnly = true)
    List<EventShortDto> getEvents(Optional<String> text,
                                  Optional<List<Long>> categories,
                                  Optional<Boolean> paid,
                                  Optional<String> rangeStart,
                                  Optional<String> rangeEnd,
                                  Optional<Boolean> onlyAvailable,
                                  String sort,
                                  Integer from,
                                  Integer size,
                                  HttpServletRequest request);

    EventFullDto getEventById(Long id, HttpServletRequest request);
}
