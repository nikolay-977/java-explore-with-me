package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.model.dto.CompilationDto;

import java.util.List;

public interface PublicCompilationsService {
    List<CompilationDto> getCompilation(Boolean pinned, Integer from, Integer size);

    CompilationDto getCompilationById(Long compId);
}
