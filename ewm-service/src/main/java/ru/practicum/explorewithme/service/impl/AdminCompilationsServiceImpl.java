package ru.practicum.explorewithme.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.exception.NotFoundException;
import ru.practicum.explorewithme.mapper.CompilationMapper;
import ru.practicum.explorewithme.model.Compilation;
import ru.practicum.explorewithme.model.Event;
import ru.practicum.explorewithme.model.dto.CompilationDto;
import ru.practicum.explorewithme.model.dto.NewCompilationDto;
import ru.practicum.explorewithme.repository.CompilationsRepository;
import ru.practicum.explorewithme.repository.EventsRepository;
import ru.practicum.explorewithme.service.AdminCompilationsService;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;

import static ru.practicum.explorewithme.utils.Constants.COMPILATION_NOT_FOUND_MESSAGE;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminCompilationsServiceImpl implements AdminCompilationsService {

    private final CompilationsRepository compilationsRepository;
    private final EventsRepository eventsRepository;

    @Transactional
    @Override
    public CompilationDto addCompilation(NewCompilationDto newCompilationDto) {

        Set<Event> events = new HashSet<>();

        if (!newCompilationDto.getEventIdList().isEmpty()) {
            events.addAll(eventsRepository.findEventsByIdIn(newCompilationDto.getEventIdList()));
        }

        CompilationDto compilationDto = CompilationMapper
                .toCompilationDto(compilationsRepository.save(CompilationMapper
                        .toCompilation(newCompilationDto, events)));
        log.info("Added compilation id={}", compilationDto.getId());
        return compilationDto;
    }

    @Transactional
    @Override
    public void deleteCompilation(Long compId) {
        findCompilation(compId);
        compilationsRepository.deleteById(compId);
        log.info("Deleted compilation id={}", compId);
    }

    @Transactional
    @Override
    public void deleteEvent(Long compId, Long eventId) {
        Compilation compilation = findCompilation(compId);
        compilation.getEvents().removeIf(e -> (e.getId().equals(eventId)));
        compilationsRepository.save(compilation);
        log.info("Deleted event id={} from compilation id={}", eventId, compId);
    }

    @Transactional
    @Override
    public void updateEvent(Long compId, Long eventId) {
        Compilation compilation = findCompilation(compId);
        Event event = findEvent(eventId);
        compilation.getEvents().add(event);
        compilationsRepository.save(compilation);
        log.info("Updated event id={} from compilation id={}", eventId, compId);
    }

    @Transactional
    @Override
    public void unpinCompilation(Long compId) {
        Compilation compilation = findCompilation(compId);
        compilation.setPinned(false);
        compilationsRepository.save(compilation);
        log.info("Unpinned compilation id={}", compId);
    }

    @Transactional
    @Override
    public void pinCompilation(Long compId) {
        Compilation compilation = findCompilation(compId);
        compilation.setPinned(true);
        compilationsRepository.save(compilation);
        log.info("Pinned compilation id={}", compId);
    }

    private Compilation findCompilation(Long id) {
        return compilationsRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(MessageFormat.format("{0}{1}", COMPILATION_NOT_FOUND_MESSAGE, id)));
    }

    private Event findEvent(Long id) {
        return eventsRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(MessageFormat.format("{0}{1}", COMPILATION_NOT_FOUND_MESSAGE, id)));
    }
}
