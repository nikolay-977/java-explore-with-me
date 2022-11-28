package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.model.dto.CompilationDto;
import ru.practicum.explorewithme.model.dto.NewCompilationDto;

public interface AdminCompilationsService {
    CompilationDto addCompilation(NewCompilationDto newCompilationDto);

    void deleteCompilation(Long compId);

    void deleteEvent(Long compId, Long eventId);

    void updateEvent(Long compId, Long eventId);

    void unpinCompilation(Long compId);

    void pinCompilation(Long compId);
}
